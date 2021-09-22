package org.apsarasmc.sponge.legacy.command.util;

import org.apsarasmc.apsaras.command.CommandSender;
import org.apsarasmc.sponge.legacy.command.SpongeSender;
import org.apsarasmc.sponge.legacy.entity.SpongePlayer;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

public class CommandSenderUtil {
  private CommandSenderUtil() {
    //
  }
  public static CommandSender transfer(CommandSource commandSender){
    if(commandSender instanceof Player){
      return new SpongePlayer((Player) commandSender);
    }else {
      return new SpongeSender(commandSender);
    }
  }
}
