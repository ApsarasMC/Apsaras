package org.apsarasmc.spigot.event.lifecycle;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.command.Command;
import org.apsarasmc.apsaras.event.EventContext;
import org.apsarasmc.apsaras.event.lifecycle.LoadPluginEvent;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.spigot.command.SpigotCommandManager;

public abstract class SpigotLoadPluginEvent implements LoadPluginEvent {
  private final EventContext context = EventContext.builder().build();
  @Override
  public EventContext context() {
    return context;
  }
  public static class RegisterCommand extends SpigotLoadPluginEvent implements LoadPluginEvent.RegisterCommand {
    @Override
    public void register(PluginContainer plugin, String name, Command command) {
      Apsaras.injector().getInstance(SpigotCommandManager.class).registerCommand(plugin,name,command);
    }
  }
}
