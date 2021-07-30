package org.apsarasmc.sponge;


import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.Server;
import org.apsarasmc.apsaras.scheduler.SchedulerService;
import org.apsarasmc.plugin.ImplGame;
import org.apsarasmc.sponge.event.Handlers;
import org.apsarasmc.sponge.scheduler.SyncScheduler;
import org.apsarasmc.sponge.scheduler.UtsScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

@Singleton
public class SpongeCore implements Server {
  private final org.spongepowered.plugin.PluginContainer wrapper;
  @Inject
  private SyncScheduler syncScheduler;
  @Inject
  private UtsScheduler utsScheduler;
  @Inject
  private Handlers handlers;

  public SpongeCore(final org.spongepowered.plugin.PluginContainer wrapper) {
    this.wrapper = wrapper;
  }

  public void init() {
    new ImplGame(new SpongeModule(binder -> {
      binder.bind(Server.class).toInstance(this);
      binder.bind(SpongeCore.class).toInstance(this);
    }));
    Apsaras.injector().inject(this);

    try {
      Path pluginsPath = this.pluginPath().resolve("plugins");
      Files.createDirectories(pluginsPath);
      Arrays.stream(Objects.requireNonNull(
        pluginsPath.toFile().listFiles())
      ).filter(file -> file.getName().endsWith(".jar")).forEach(file -> Apsaras.pluginManager().addPlugin(file));
    } catch (Exception e) {
      this.logger().warn("Failed to open plugins dir.", e);
    }
    Apsaras.pluginManager().load();
    Apsaras.pluginManager().enable();

    Sponge.eventManager().registerListeners(this.wrapper, this);
    this.handlers.register();
  }

  public org.spongepowered.plugin.PluginContainer wrapper() {
    return this.wrapper;
  }

  @Override
  public SchedulerService sync() {
    return this.syncScheduler;
  }

  @Override
  public SchedulerService uts() {
    return this.utsScheduler;
  }

  @Override
  public Logger logger() {
    return LoggerFactory.getLogger(Apsaras.NAME);
  }

  @Override
  public ClassLoader classLoader() {
    return SpongeCore.class.getClassLoader();
  }

  @Override
  public Path gamePath() {
    return Sponge.game().gameDirectory();
  }

  @Override
  public Path pluginPath() {
    return Sponge.game().gameDirectory().resolve("apsaras");
  }

  @Override
  public String version() {
    return "Sponge" + this.wrapper().metadata().version();
  }
}
