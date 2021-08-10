package org.apsarasmc.spigot;

import com.google.common.collect.ImmutableList;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.command.CommandManager;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.scheduler.SchedulerService;
import org.apsarasmc.plugin.ImplGame;
import org.apsarasmc.plugin.ImplServer;
import org.apsarasmc.plugin.util.relocate.RelocatingRemapper;
import org.apsarasmc.plugin.util.relocate.Relocation;
import org.apsarasmc.spigot.event.Transfers;
import org.apsarasmc.spigot.event.lifecycle.SpigotLoadPluginEvent;
import org.apsarasmc.spigot.scheduler.SyncScheduler;
import org.apsarasmc.spigot.scheduler.UtsScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class SpigotCore implements ImplServer, Listener {
  private static final RelocatingRemapper remapper = new RelocatingRemapper(
    ImmutableList.< Relocation >builder()
      .add(new Relocation("org{}sonatype{}inject", "org{}apsarasmc{}libs{}sonatype{}inject"))
      .add(new Relocation("org{}eclipse{}sisu", "org{}apsarasmc{}libs{}eclipse{}sisu"))
      .add(new Relocation("org{}eclipse{}aether", "org{}apsarasmc{}libs{}eclipse{}aether"))
      .add(new Relocation("org{}codehaus{}plexus", "org{}apsarasmc{}libs{}codehaus{}plexus"))
      .add(new Relocation("org{}apache{}maven", "org{}apsarasmc{}libs{}apache{}maven"))
      .add(new Relocation("org{}apache{}http", "org{}apsarasmc{}libs{}apache{}http"))
      .add(new Relocation("org{}apache{}commons", "org{}apsarasmc{}libs{}apache{}commons"))
      .add(new Relocation("org{}aopalliance", "org{}apsarasmc{}libs{}aopalliance"))
      .add(new Relocation("com{}google{}inject", "org{}apsarasmc{}libs{}guice"))
      .add(new Relocation("org{}yaml{}snakeyaml", "org{}apsarasmc{}libs{}snakeyaml"))

      .add(new Relocation("org{}apsarasmc{}testplugin{}aaa", "org{}apsarasmc{}testplugin{}bbb"))
      .build()
  );
  private JavaPlugin wrapper;
  @Inject
  private ImplGame game;
  @Inject
  private SyncScheduler syncScheduler;
  @Inject
  private UtsScheduler utsScheduler;
  @Inject
  private Transfers transfers;
  @Inject
  private CommandManager commandManager;
  @Inject
  private EventManager eventManager;

  public SpigotCore(final JavaPlugin wrapper) {
    this.wrapper = wrapper;
  }

  public JavaPlugin wrapper() {
    return this.wrapper;
  }

  public void enable() {
    new ImplGame(new SpigotModule(binder -> {
      binder.bind(ImplServer.class).toInstance(this);
      binder.bind(SpigotCore.class).toInstance(this);
      binder.bind(BukkitAudiences.class).toInstance(BukkitAudiences.create(this.wrapper));
    }));
    Apsaras.injector().injectMembers(this);

    try {
      Path pluginsPath = this.pluginPath().resolve("plugins");
      Files.createDirectories(pluginsPath);
      Arrays.stream(
        Objects.requireNonNull(pluginsPath.toFile().listFiles())
      ).filter(file -> file.getName().endsWith(".jar")).forEach(file -> Apsaras.pluginManager().addPlugin(file));
    } catch (Exception e) {
      this.logger().warn("Failed to open plugins dir.", e);
    }
    this.transfers.register();

    game.enable();
    eventManager.post(new SpigotLoadPluginEvent.RegisterCommand());
  }

  public void disable() {
    game.disable();
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
    return LoggerFactory.getLogger("apsaras");
  }

  @Override
  public ClassLoader classLoader() {
    return SpigotCore.class.getClassLoader();
  }

  @Override
  public CommandManager commandManager() {
    return this.commandManager;
  }

  @Override
  public Path gamePath() {
    return new File(".").toPath();
  }

  @Override
  public Path pluginPath() {
    return this.gamePath().resolve("apsaras");
  }

  @Override
  public String version() {
    return wrapper.getDescription().getVersion();
  }

  @Override
  public RelocatingRemapper remapper() {
    return remapper;
  }
}
