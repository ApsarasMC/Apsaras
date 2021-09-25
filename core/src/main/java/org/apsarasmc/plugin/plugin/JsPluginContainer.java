package org.apsarasmc.plugin.plugin;

import com.google.gson.Gson;
import com.google.inject.Injector;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.plugin.PluginDepend;
import org.apsarasmc.apsaras.tasker.SyncTasker;
import org.apsarasmc.plugin.ImplServer;
import org.apsarasmc.plugin.aop.ImplInjector;
import org.apsarasmc.plugin.dependency.DependencyResolver;
import org.apsarasmc.plugin.event.lifecycle.ImplPluginLifeEvent;
import org.apsarasmc.plugin.util.LineWriter;
import org.apsarasmc.plugin.util.StaticEntryUtil;
import org.openjdk.nashorn.api.scripting.NashornScriptEngine;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Path;

public class JsPluginContainer extends ImplPluginContainer {
  @Inject
  private DependencyResolver dependencyResolver;
  @Inject
  private EventManager eventManager;
  @Inject
  private ImplServer server;
  @Inject
  private SyncTasker tasker;
  private ImplPluginLoader pluginLoader;
  private Injector injector;

  private static final Gson gson = new Gson();
  private final File jsPath;

  private NashornScriptEngine engine;

  public JsPluginContainer(final @Nonnull File jsPath) {
    Apsaras.injector().injectMembers(this);
    this.jsPath = jsPath;
  }

  @Override
  public ImplPluginLoader pluginLoader() {
    return null;
  }

  @Override
  public void load() throws Exception {
    this.pluginLoader = new ImplPluginLoader(
      new URL[]{ jsPath.toURI().toURL() },
      Apsaras.server().classLoader(),
      Apsaras.server().apiClassLoader(),
      server.remapper()
    );
    loadMeta(new FileReader(jsPath.toPath().resolve("apsaras.json").toFile()));

    final Logger logger = logger();
    this.engine = (NashornScriptEngine) new NashornScriptEngineFactory().getScriptEngine(pluginLoader);
    this.engine.getContext().setWriter(new LineWriter(logger::info));
    this.engine.getContext().setErrorWriter(new LineWriter(logger::error));

    for (PluginDepend depend : meta().depends()) {
      if (depend.type().equals(PluginDepend.Type.LIBRARY)) {
        this.pluginLoader.addURL(
          dependencyResolver.getDependencyFile(depend.name()).toURI().toURL()
        );
      }
    }
    eventManager.post(new ImplPluginLifeEvent.Load(this));
  }

  @Override
  public void enable() throws Exception {
    super.enable();
    StaticEntryUtil.applyPluginContainer(this.pluginLoader, this);

    this.injector = ((ImplInjector)Apsaras.injector()).handle()
      .createChildInjector(binder -> {
        binder.bind(PluginContainer.class).toInstance(this);
        binder.bind(Logger.class).toInstance(logger());
      });
    StaticEntryUtil.applyInjector(this.pluginLoader, new ImplInjector(injector));
    require(meta().main());
    eventManager.post(new ImplPluginLifeEvent.Enable(this));
  }

  public Object require(String name) {
    Path path = jsPath.toPath().resolve(name);
    if(!path.startsWith(jsPath.toPath())){
      throw new IllegalArgumentException("Dangerous name.");
    }
    try(Reader reader = new FileReader(path.toFile())) {
      return engine.invokeFunction("load", "classpath:" + name);
    } catch (IOException | ScriptException | NoSuchMethodException e) {
      throw new IllegalStateException("Failed to eval javascript.", e);
    }
  }

  @Override
  public void disable() throws Exception {
    this.eventManager.unregisterPluginListeners(this);
  }

  @Override
  public String toString() {
    return name();
  }
}
