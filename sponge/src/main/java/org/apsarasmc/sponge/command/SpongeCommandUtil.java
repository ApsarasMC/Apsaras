package org.apsarasmc.sponge.command;

import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.command.CommandResult;
import org.apsarasmc.apsaras.command.CommandSender;
import org.apsarasmc.sponge.entity.SpongePlayer;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

public class SpongeCommandUtil {
  private SpongeCommandUtil() {
    //
  }
  public static org.spongepowered.api.command.CommandResult transfer(CommandResult input){
    if(input.isSuccess()){
      return org.spongepowered.api.command.CommandResult.success();
    }else {
      if(input.errorMessage().isPresent()){
        return org.spongepowered.api.command.CommandResult.error(input.errorMessage().get());
      }
      return org.spongepowered.api.command.CommandResult.error(Component.empty());
    }
  }

  public static CommandSender sender(CommandCause input){
    if(input.root() instanceof Player){
      return new SpongePlayer((ServerPlayer) input.root());
    }
    return new SpongeSender(input);
  }
}
