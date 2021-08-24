package org.apsarasmc.sponge.event.lifecycle;

import org.apsarasmc.apsaras.command.Command;
import org.apsarasmc.apsaras.event.EventContext;
import org.apsarasmc.apsaras.event.lifecycle.LoadPluginEvent;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.sponge.command.SpongeAdventureUtil;
import org.apsarasmc.sponge.command.SpongeRawCommand;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpongeLoadPluginEvent implements LoadPluginEvent {
  private final EventContext context = EventContext.builder().build();
  @Override
  public EventContext context() {
    return context;
  }
  public static class RegisterCommand extends SpongeLoadPluginEvent implements LoadPluginEvent.RegisterCommand, Closeable {
    private Map< String, org.spongepowered.api.command.Command.Raw> rawCommands = new HashMap<>();
    private Map< String, org.spongepowered.api.command.Command.Parameterized > parameterizedCommands = new HashMap<>();

    @Override
    public void register(PluginContainer plugin, String name, Command.Raw command) {
      if(rawCommands == null) {
        throw new IllegalStateException("Can't register raw command outside this event. DON'T SAVE EVENT!");
      }
      rawCommands.put(name, new SpongeRawCommand(command));
    }

    @Override
    public void register(PluginContainer plugin, String name, Command.Adventure command) {
      if(parameterizedCommands == null) {
        throw new IllegalStateException("Can't register adventure command outside this event. DON'T SAVE EVENT!");
      }
      parameterizedCommands.put(name, SpongeAdventureUtil.transfer(command));
    }

    public Map< String, org.spongepowered.api.command.Command.Raw > rawCommands() {
      return rawCommands;
    }

    public Map< String, org.spongepowered.api.command.Command.Parameterized > parameterizedCommands() {
      return parameterizedCommands;
    }

    public void closeRaw(){
      rawCommands = null;
    }

    public void closeParameterized(){
      parameterizedCommands = null;
    }

    @Override
    public void close() {
      closeRaw();
      closeParameterized();
    }
  }
}
