package org.apsarasmc.plugin.plugin;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.plugin.PluginDepend;
import org.apsarasmc.apsaras.plugin.PluginMeta;
import org.apsarasmc.plugin.ImplServer;
import org.apsarasmc.plugin.dependency.DependencyResolver;
import org.apsarasmc.plugin.event.lifecycle.ImplPluginLifeEvent;
import org.apsarasmc.plugin.util.ImplPrefixLogger;
import org.apsarasmc.plugin.util.StaticEntryUtil;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class JavaPluginContainer implements PluginContainer {
  private static final Gson gson = new Gson();
  private final File jarFile;
  @Inject
  private DependencyResolver dependencyResolver;
  @Inject
  private EventManager eventManager;
  @Inject
  private ImplServer server;
  private boolean enable = false;
  private JavaPluginLoader pluginLoader;
  private PluginMeta meta;
  private Set< String > components;
  private Logger logger;
  private Injector injector;

  public JavaPluginContainer(final @Nonnull File jarFile) {
    Apsaras.injector().injectMembers(this);
    this.jarFile = jarFile;
  }

  @Override
  public @Nonnull
  PluginMeta meta() {
    return this.meta;
  }

  public JavaPluginLoader pluginLoader() {
    return pluginLoader;
  }

  @Override
  public Logger logger() {
    return this.logger;
  }

  @Override
  public void addDepend(final @Nonnull PluginContainer depend) {
    if (!(depend instanceof JavaPluginContainer)) {
      throw new IllegalArgumentException("Only java plugin could depend on.");
    }
    this.pluginLoader.addDepend(((JavaPluginContainer) depend).pluginLoader);
  }

  @Override
  public boolean enabled() {
    return this.enable;
  }

  public Injector injector() {
    return injector;
  }

  @Override
  public void load() throws Exception {
    this.pluginLoader = new JavaPluginLoader(jarFile.toURI().toURL(), Apsaras.server().classLoader());
    loadMeta();
    loadComponents();
    for (PluginDepend depend : this.meta.depends()) {
      if (depend.type().equals(PluginDepend.Type.LIBRARY)) {
        this.pluginLoader.addURL(
          dependencyResolver.getDependencyFile(depend.name()).toURI().toURL()
        );
      }
    }
    eventManager.post(new ImplPluginLifeEvent.Load(this));
  }

  private void loadMeta() throws Exception {
    InputStream metaStream = this.pluginLoader.getResourceAsStream("META-INF/apsaras.json");
    if (metaStream == null) {
      throw new FileNotFoundException("No META-INF/apsaras.json found in " + jarFile);
    }
    InputStreamReader metaReader = new InputStreamReader(metaStream);
    this.meta = gson.fromJson(metaReader, ImplPluginMeta.class);
    metaReader.close();
    metaStream.close();
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

  @Override
  public void enable() throws Exception {
    this.enable = true;
    this.logger = new ImplPrefixLogger(Apsaras.server().logger(), "[" + this.name() + "]: ");
    this.pluginLoader.remapper(server.remapper());
    StaticEntryUtil.applyPluginContainer(this.pluginLoader, this);

    Class< ? > mainClass = this.pluginLoader.loadClass(this.meta.main());
    this.injector = Apsaras.injector()
      .createChildInjector(binder -> {
        binder.bind(PluginContainer.class).toInstance(this);
        binder.bind(Logger.class).toInstance(this.logger);
        try {
          ((Module) mainClass.getConstructor().newInstance()).configure(binder);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
          logger.warn("Failed to constructor {}'s Module class {}.", this.name(), mainClass.getName());
        }
      });
    StaticEntryUtil.applyInjector(this.pluginLoader, injector);
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
      logger.warn("Failed to create data directory for " + this.name() + ".", e);
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
      logger.warn("Failed to create config directory for " + this.name() + ".", e);
    }
    return server.pluginPath().resolve("config").resolve(this.name());
  }

  @Override
  public @Nonnull
  String name() {
    return this.meta == null ? this.jarFile.getName() : this.meta.name();
  }

  @Override
  public String toString() {
    return this.name();
  }
}
