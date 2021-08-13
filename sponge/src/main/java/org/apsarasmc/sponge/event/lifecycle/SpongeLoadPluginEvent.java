package org.apsarasmc.sponge.event.lifecycle;

import org.apsarasmc.apsaras.command.Command;
import org.apsarasmc.apsaras.event.EventContext;
import org.apsarasmc.apsaras.event.lifecycle.LoadPluginEvent;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;

public class SpongeLoadPluginEvent implements LoadPluginEvent {
  private final EventContext context = EventContext.builder().build();
  @Override
  public EventContext context() {
    return context;
  }
  public static class RegisterCommand extends SpongeLoadPluginEvent implements LoadPluginEvent.RegisterCommand {
    private final RegisterCommandEvent< org.spongepowered.api.command.Command.Raw> event;
    public RegisterCommand(final RegisterCommandEvent< org.spongepowered.api.command.Command.Raw> event){
      this.event = event;
    }

    @Override
    public void register(PluginContainer plugin, String name, Command.Raw command) {
      //
    }

    @Override
    public void register(PluginContainer plugin, String name, Command.Adventure command) {
      //
    }
  }
}
