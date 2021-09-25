package org.apsarasmc.plugin.plugin;

import com.google.gson.Gson;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.plugin.PluginDepend;
import org.apsarasmc.plugin.ImplServer;
import org.apsarasmc.plugin.aop.ImplInjector;
import org.apsarasmc.plugin.dependency.DependencyResolver;
import org.apsarasmc.plugin.event.lifecycle.ImplPluginLifeEvent;
import org.apsarasmc.plugin.util.StaticEntryUtil;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JavaPluginContainer extends ImplPluginContainer {
  private static final Gson gson = new Gson();
  private final File jarFile;
  @Inject
  private DependencyResolver dependencyResolver;
  @Inject
  private EventManager eventManager;
  @Inject
  private ImplServer server;
  private ImplPluginLoader pluginLoader;
  private Set< String > components;
  private Collection< String > apis;
  private Injector injector;

  public JavaPluginContainer(final @Nonnull File jarFile) {
    Apsaras.injector().injectMembers(this);
    this.jarFile = jarFile;
  }

  @Override
  public ImplPluginLoader pluginLoader() {
    return pluginLoader;
  }

  public Injector injector() {
    return injector;
  }

  @Override
  public void load() throws Exception {
    this.pluginLoader = new ImplPluginLoader(
      new URL[]{},
      Apsaras.server().classLoader(),
      Apsaras.server().apiClassLoader(),
      server.remapper()
    );
    InputStream metaStream = this.pluginLoader.getResourceAsStream("META-INF/apsaras.json");
    if (metaStream == null) {
      throw new FileNotFoundException("No apsaras.json found in " + jarFile);
    }
    loadMeta(new InputStreamReader(metaStream));
    metaStream.close();

    loadComponents();

    loadApis();

    for (String api : apis) {
      this.pluginLoader.addApi(api);
    }
    for (PluginDepend depend : meta().depends()) {
      if (depend.type().equals(PluginDepend.Type.LIBRARY)) {
        this.pluginLoader.addURL(
          dependencyResolver.getDependencyFile(depend.name()).toURI().toURL()
        );
      }
    }
    eventManager.post(new ImplPluginLifeEvent.Load(this));
  }

  private void loadComponents() throws Exception {
    InputStream metaStream = this.pluginLoader.getResourceAsStream("META-INF/components.json");
    if (metaStream == null) {
      throw new FileNotFoundException("No META-INF/components.json found in " + jarFile);
    }
    InputStreamReader metaReader = new InputStreamReader(metaStream);
    this.components = gson.fromJson(metaReader, Set.class);
    metaReader.close();
    metaStream.close();
  }

  private void loadApis() throws Exception {
    InputStream metaStream = this.pluginLoader.getResourceAsStream("META-INF/apis.json");
    if (metaStream == null) {
      this.apis = Collections.emptyList();
    }else{
      InputStreamReader metaReader = new InputStreamReader(metaStream);
      this.apis = gson.fromJson(metaReader, List.class);
      metaReader.close();
      metaStream.close();
    }
  }

  @Override
  public void enable() throws Exception {
    super.enable();
    StaticEntryUtil.applyPluginContainer(this.pluginLoader, this);

    Class< ? > mainClass = this.pluginLoader.loadClass(meta().main());
    this.injector = ((ImplInjector)Apsaras.injector()).handle()
      .createChildInjector(binder -> {
        binder.bind(PluginContainer.class).toInstance(this);
        binder.bind(Logger.class).toInstance(logger());
        try {
          ((Module) mainClass.getConstructor().newInstance()).configure(binder);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
          logger().warn("Failed to constructor {}'s Module class {}.", this.name(), mainClass.getName());
        }
      });
    StaticEntryUtil.applyInjector(this.pluginLoader, new ImplInjector(injector));
    for (String componentName : this.components) {
      Class< ? > componentClass = this.pluginLoader.loadClass(componentName);
      Object component = this.injector.getInstance(componentClass);
      this.eventManager.registerListeners(this, component);
    }
    eventManager.post(new ImplPluginLifeEvent.Enable(this));
  }

  @Override
  public void disable() throws Exception {
    this.eventManager.unregisterPluginListeners(this);
  }

  @Override
  public Path dataPath() {
    Path dataPath = server.pluginPath().resolve("storage").resolve(this.name());
    try {
      if(Files.notExists(dataPath)){
        Files.createDirectories(dataPath);
      }
    } catch (IOException e) {
      logger().warn("Failed to create data directory for " + this.name() + ".", e);
    }
    return dataPath;
  }

  @Override
  public Path configPath() {
    Path dataPath = server.pluginPath().resolve("config").resolve(this.name());
    try {
      if(Files.notExists(dataPath)){
        Files.createDirectories(dataPath);
      }
    } catch (IOException e) {
      logger().warn("Failed to create config directory for " + this.name() + ".", e);
    }
    return server.pluginPath().resolve("config").resolve(this.name());
  }

  @Override
  public String toString() {
    return this.name();
  }
}
