package org.apsarasmc.sponge;


import com.google.common.collect.ImmutableList;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.scheduler.SyncScheduler;
import org.apsarasmc.apsaras.scheduler.UtsScheduler;
import org.apsarasmc.apsaras.tasker.SyncTasker;
import org.apsarasmc.apsaras.tasker.UtsTasker;
import org.apsarasmc.plugin.ImplGame;
import org.apsarasmc.plugin.ImplServer;
import org.apsarasmc.plugin.util.relocate.RelocatingRemapper;
import org.apsarasmc.plugin.util.relocate.Relocation;
import org.apsarasmc.sponge.event.Transfers;
import org.apsarasmc.sponge.tasker.SpongeSyncTasker;
import org.apsarasmc.sponge.tasker.SpongeUtsTasker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.event.EventListenerRegistration;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StoppedGameEvent;
import org.spongepowered.api.placeholder.PlaceholderContext;
import org.spongepowered.api.placeholder.PlaceholderParser;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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
  private final org.spongepowered.plugin.PluginContainer wrapper;
  @Inject
  private ImplGame game;
  @Inject
  private SyncTasker syncTasker;
  @Inject
  private UtsTasker utsTasker;
  @Inject
  private SyncTasker syncScheduler;
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

    this.transfers.register();
    game.enable();
    Sponge.eventManager().registerListener(
      EventListenerRegistration
        .builder(StoppedGameEvent.class)
        .plugin(this.wrapper)
        .listener(e-> game.disable())
        .order(Order.DEFAULT)
        .build()
    );
    Sponge.eventManager().registerListeners(this.wrapper, this);
  }

  public static Parameter.Value< Integer > ipParameter;
  @Listener
  public void onCommandRegister(final RegisterCommandEvent<Command.Parameterized> event){
    ipParameter = Parameter.integerNumber().key("aaa").build();
    event.register(
      this.wrapper,
      Command
        .builder()
        .addParameter(Parameter.bool().key("cyc").build())
        .executor((context -> {
          context.sendMessage(Identity.nil(), Component.text("wwwwwwwwww"));
          logger().info("{}",context.requireOne(ipParameter));
          return CommandResult.success();
        }))
        .build(),
      "cccccccccccc");
  }

  public org.spongepowered.plugin.PluginContainer wrapper() {
    return this.wrapper;
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
    return null;
  }

  @Override
  public UtsScheduler utsScheduler() {
    return null;
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
  public void dispatchCommand(String command) {
    try {
      Sponge.server().commandManager().process(command);
    } catch (CommandException e) {
      logger().warn("Failed to dispatch command.", e);
    }
  }

  @Override
  public RelocatingRemapper remapper() {
    return remapper;
  }
}
