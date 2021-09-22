package org.apsarasmc.sponge.legacy.event.lifecycle;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.command.Command;
import org.apsarasmc.apsaras.event.EventContext;
import org.apsarasmc.apsaras.event.lifecycle.LoadPluginEvent;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.sponge.legacy.SpongeCore;
import org.apsarasmc.sponge.legacy.command.SpongeAdventureCommand;
import org.apsarasmc.sponge.legacy.command.SpongeRawCommand;
import org.spongepowered.api.Sponge;

import java.io.Closeable;

public class SpongeLoadPluginEvent implements LoadPluginEvent {
  private final EventContext context = EventContext.builder().build();
  @Override
  public EventContext context() {
    return context;
  }
  public static class RegisterCommand extends SpongeLoadPluginEvent implements LoadPluginEvent.RegisterCommand, Closeable {
    @Override
    public void register(PluginContainer plugin, String name, Command.Raw command) {
      Sponge.getCommandManager().register(Apsaras.injector().getInstance(SpongeCore.class).wrapperInstance(),
        new SpongeRawCommand(name, command),
        name
      );
    }

    @Override
    public void register(PluginContainer plugin, String name, Command.Adventure command) {
      Sponge.getCommandManager().register(Apsaras.injector().getInstance(SpongeCore.class).wrapperInstance(),
        new SpongeAdventureCommand(name, command),
        name
      );
    }

    @Override
    public void close() {
      //
    }
  }
}
