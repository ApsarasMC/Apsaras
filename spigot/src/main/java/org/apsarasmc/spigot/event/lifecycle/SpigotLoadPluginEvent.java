package org.apsarasmc.spigot.event.lifecycle;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.command.Command;
import org.apsarasmc.apsaras.event.EventContext;
import org.apsarasmc.apsaras.event.lifecycle.LoadPluginEvent;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.spigot.command.SpigotAdventureCommand;
import org.apsarasmc.spigot.command.SpigotRawCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import org.slf4j.Logger;

import java.lang.reflect.Field;

public abstract class SpigotLoadPluginEvent implements LoadPluginEvent {
  private final EventContext context = EventContext.builder().build();
  @Override
  public EventContext context() {
    return context;
  }
  public static class RegisterCommand extends SpigotLoadPluginEvent implements LoadPluginEvent.RegisterCommand {
    private final Logger logger = Apsaras.server().logger();
    private SimpleCommandMap commandMap;
    public RegisterCommand(){
      try {
        Field commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
        commandMapField.setAccessible(true);
        commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getPluginManager());
      } catch (ClassCastException | NoSuchFieldException | IllegalAccessException e) {
        logger.warn("Failed to get CommandMap by reflection. F**k you, Spigot.", e);
      }
    }
    @Override
    public void register(PluginContainer plugin, String name, Command.Raw command) {
      commandMap.register(plugin.name(), new SpigotRawCommand(name, command));
    }

    @Override
    public void register(PluginContainer plugin, String name, Command.Adventure command) {
      commandMap.register(plugin.name(), new SpigotAdventureCommand(name, command));
    }
  }
}
