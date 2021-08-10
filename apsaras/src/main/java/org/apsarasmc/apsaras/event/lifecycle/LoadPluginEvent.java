package org.apsarasmc.apsaras.event.lifecycle;

import org.apsarasmc.apsaras.command.Command;
import org.apsarasmc.apsaras.event.Event;
import org.apsarasmc.apsaras.plugin.PluginContainer;

public interface LoadPluginEvent extends Event {
  interface RegisterCommand extends LoadPluginEvent {
    void register(PluginContainer plugin, String name, Command.Raw command);
    void register(PluginContainer plugin, String name, Command.Adventure command);
  }
}
