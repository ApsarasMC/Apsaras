package org.apsarasmc.sponge.legacy;


import com.google.common.collect.ImmutableList;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.scheduler.SyncScheduler;
import org.apsarasmc.apsaras.scheduler.UtsScheduler;
import org.apsarasmc.apsaras.tasker.SyncTasker;
import org.apsarasmc.apsaras.tasker.UtsTasker;
import org.apsarasmc.plugin.ImplGame;
import org.apsarasmc.plugin.ImplServer;
import org.apsarasmc.plugin.util.relocate.RelocatingRemapper;
import org.apsarasmc.plugin.util.relocate.Relocation;
import org.apsarasmc.sponge.legacy.event.Transfers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.game.state.GameStoppedEvent;
import org.spongepowered.api.plugin.PluginContainer;

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
      .add(new Relocation("org{}objectweb{}asm", "org{}apsarasmc{}libs{}asm"))
      .add(new Relocation("com{}zaxxer{}hikari", "org{}apsarasmc{}libs{}hikari"))
      .add(new Relocation("com{}mchange", "org{}apsarasmc{}libs{}mchange"))
      .add(new Relocation("org{}quartz", "org{}apsarasmc{}libs{}quartz"))
      .add(new Relocation("org{}terracotta", "org{}apsarasmc{}libs{}terracotta"))
      .build()
  );
  private final PluginContainer wrapper;
  private final Object instance;
  @Inject
  private ImplGame game;
  @Inject
  private SyncTasker syncTasker;
  @Inject
  private UtsTasker utsTasker;
  @Inject
  private SyncScheduler syncScheduler;
  @Inject
  private UtsScheduler utsScheduler;
  @Inject
  private Transfers transfers;

  public SpongeCore(final PluginContainer wrapper, final Object instance) {
    this.wrapper = wrapper;
    this.instance = instance;
  }

  public void init() {
    new ImplGame(new SpongeModule(binder -> {
      binder.bind(ImplServer.class).toInstance(this);
      binder.bind(SpongeCore.class).toInstance(this);
      binder.bind(Game.class).toInstance(Sponge.getGame());
      binder.bind(PluginContainer.class).toInstance(wrapper);
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

    try {
      Path pluginsPath = this.gamePath().resolve("mods");
      Files.createDirectories(pluginsPath);
      Arrays.stream(
        Objects.requireNonNull(pluginsPath.toFile().listFiles())
      ).filter(file -> file.getName().endsWith(".jar")).forEach(file -> Apsaras.pluginManager().addPlugin(file));
    } catch (Exception e) {
      this.logger().warn("Failed to open plugins dir.", e);
    }

    this.transfers.register();
    game.enable();
    Sponge.getEventManager().registerListener(
      wrapperInstance(),
      GameStoppedEvent.class,
      e -> game.disable()
    );
  }

  public PluginContainer wrapper() {
    return this.wrapper;
  }

  public Object wrapperInstance() {
    return instance;
  }

  @Override
  public SyncTasker syncTasker() {
    return this.syncTasker;
  }

  @Override
  public UtsTasker utsTasker() {
    return this.utsTasker;
  }

  @Override
  public SyncScheduler syncScheduler() {
    return syncScheduler;
  }

  @Override
  public UtsScheduler utsScheduler() {
    return utsScheduler;
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
  public ClassLoader apiClassLoader() {
    return classLoader().getParent();
  }

  @Override
  public Path gamePath() {
    return Sponge.getGame().getGameDirectory();
  }

  @Override
  public Path pluginPath() {
    return Sponge.getGame().getGameDirectory().resolve("apsaras");
  }

  @Override
  public String version() {
    return this.wrapper().getVersion().orElseThrow(()->new IllegalStateException("No Apsaras version found."));
  }

  @Override
  public void dispatchCommand(String command) {
    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), command);
  }

  @Override
  public RelocatingRemapper remapper() {
    return remapper;
  }
}
