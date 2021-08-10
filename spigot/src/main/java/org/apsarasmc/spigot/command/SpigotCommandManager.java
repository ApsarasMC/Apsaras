package org.apsarasmc.spigot.command;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.command.Command;
import org.apsarasmc.apsaras.command.CommandManager;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.spigot.SpigotCore;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Field;

@Singleton
public class SpigotCommandManager implements CommandManager {
  @Inject
  private SpigotCore core;

  private SimpleCommandMap commandMap;
  public SpigotCommandManager() {
    try {
      Field commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
      commandMapField.setAccessible(true);
      commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer().getPluginManager());
    }catch (Exception e) {
      Apsaras.server().logger().warn("Failed to get CommandMap by reflection. Thank you, the f**king spigot.");
    }
  }

  public void registerCommand(PluginContainer plugin, String name, Command command) {
    commandMap.register(plugin.name(), new InnerBukkitCommand(command,name));
  }
}
