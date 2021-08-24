package org.apsarasmc.spigot.command.util;

import org.apsarasmc.apsaras.command.CommandSender;
import org.apsarasmc.spigot.entity.SpigotPlayer;
import org.apsarasmc.spigot.command.SpigotSender;
import org.bukkit.entity.Player;

public class CommandSenderUtil {
  private CommandSenderUtil() {
    //
  }
  public static CommandSender transfer(org.bukkit.command.CommandSender commandSender){
    if(commandSender instanceof Player){
      return new SpigotPlayer((Player) commandSender);
    }else {
      return new SpigotSender(commandSender);
    }
  }
}
