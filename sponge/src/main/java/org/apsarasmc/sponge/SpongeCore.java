package org.apsarasmc.sponge;


import com.google.common.collect.ImmutableList;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.scheduler.SchedulerService;
import org.apsarasmc.plugin.ImplGame;
import org.apsarasmc.plugin.ImplServer;
import org.apsarasmc.plugin.util.relocate.RelocatingRemapper;
import org.apsarasmc.plugin.util.relocate.Relocation;
import org.apsarasmc.sponge.event.Transfers;
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
public class SpongeCore implements ImplServer {
  private static final RelocatingRemapper remapper = new RelocatingRemapper(
    ImmutableList.< Relocation >builder()
      .add(new Relocation("org{}sonatype{}inject", "org{}apsarasmc{}libs{}sonatype{}inject"))
      .add(new Relocation("org{}eclipse{}sisu", "org{}apsarasmc{}libs{}eclipse{}sisu"))
      .add(new Relocation("org{}eclipse{}aether", "org{}apsarasmc{}libs{}eclipse{}aether"))
      .add(new Relocation("org{}codehaus{}plexus", "org{}apsarasmc{}libs{}codehaus{}plexus"))
      .add(new Relocation("org{}apache{}maven", "org{}apsarasmc{}libs{}apache{}maven"))
      .add(new Relocation("org{}apache{}http", "org{}apsarasmc{}libs{}apache{}http"))
      .add(new Relocation("org{}apache{}commons", "org{}apsarasmc{}libs{}apache{}commons"))
      .add(new Relocation("org{}yaml{}snakeyaml", "org{}apsarasmc{}libs{}snakeyaml"))

      .add(new Relocation("org{}apsarasmc{}testplugin{}aaa", "org{}apsarasmc{}testplugin{}bbb"))
      .build()
  );
  private final org.spongepowered.plugin.PluginContainer wrapper;
  @Inject
  private SyncScheduler syncScheduler;
  @Inject
  private UtsScheduler utsScheduler;
  @Inject
  private Transfers transfers;

  public SpongeCore(final org.spongepowered.plugin.PluginContainer wrapper) {
    this.wrapper = wrapper;
  }

  public void init() {
    new ImplGame(new SpongeModule(binder -> {
      binder.bind(ImplServer.class).toInstance(this);
      binder.bind(SpongeCore.class).toInstance(this);
    }));
    Apsaras.injector().injectMembers(this);

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
    this.transfers.register();
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
    return this.wrapper().metadata().version();
  }

  @Override
  public RelocatingRemapper remapper() {
    return remapper;
  }
}
