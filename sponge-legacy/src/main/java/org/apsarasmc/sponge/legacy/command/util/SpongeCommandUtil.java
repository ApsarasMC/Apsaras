package org.apsarasmc.sponge.legacy.command.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apsarasmc.apsaras.command.CommandResult;
import org.apsarasmc.apsaras.command.CommandSender;
import org.apsarasmc.sponge.legacy.command.SpongeSender;
import org.apsarasmc.sponge.legacy.entity.SpongePlayer;
import org.apsarasmc.sponge.legacy.util.TextComponentUtil;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class SpongeCommandUtil {
  private SpongeCommandUtil() {
    //
  }
  public static org.spongepowered.api.command.CommandResult transfer(CommandResult input) throws CommandException {
    if(input.isSuccess()){
      return org.spongepowered.api.command.CommandResult.success();
    }else {
      throw new CommandException(TextComponentUtil.toSponge(input.errorMessage()).orElseGet(
        ()-> Text.builder("Failed to invoke plugin.").color(TextColors.RED).build()
      ));
    }
  }

  public static CommandSender sender(CommandSource input){
    if(input instanceof Player){
      return new SpongePlayer((Player) input);
    }
    return new SpongeSender(input);
  }
}
