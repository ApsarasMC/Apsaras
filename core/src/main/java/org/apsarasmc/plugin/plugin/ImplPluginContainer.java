package org.apsarasmc.plugin.plugin;

import com.google.gson.Gson;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.Server;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.plugin.PluginMeta;
import org.apsarasmc.plugin.util.ImplPrefixLogger;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public abstract class ImplPluginContainer implements PluginContainer {
  private static final Gson gson = new Gson();
  @Inject
  private Server server;
  private Logger logger = null;
  private PluginMeta meta = null;
  private boolean enabled = false;

  public abstract ImplPluginLoader pluginLoader();

  @Override
  public void addDepend(final PluginContainer depend) {
    if (!(depend instanceof JavaPluginContainer)) {
      throw new IllegalArgumentException("Only java plugin could depend on.");
    }
    pluginLoader().addDepend(((JavaPluginContainer) depend).pluginLoader());
  }

  @Override
  public PluginMeta meta() {
    return Objects.requireNonNull(meta, "Plugin still not loaded.");
  }

  @Override
  public Logger logger() {
    return Objects.requireNonNull(logger, "Plugin still not loaded.");
  }

  @Override
  public boolean enabled() {
    return enabled;
  }

  protected void loadMeta(Reader metaReader) throws Exception {
    this.meta = gson.fromJson(metaReader, ImplPluginMeta.class);
    metaReader.close();
    logger = new ImplPrefixLogger(Apsaras.server().logger(), "[" + name() + "] ");
  }

  @Override
  public void enable() throws Exception {
    this.enabled = true;
  }

  @Override
  public void disable() throws Exception {
    this.enabled = false;
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
  public String name() {
    return Objects.requireNonNull(meta, "Plugin still not loaded.").name();
  }
}
