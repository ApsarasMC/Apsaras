package org.apsarasmc.sponge.command;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.command.CommandSender;
import org.spongepowered.api.command.CommandCause;

import javax.annotation.Nonnull;

public class SpongeSender implements CommandSender {
  private CommandCause sender;
  public SpongeSender(CommandCause sender){
    this.sender = sender;
  }
  @Override
  public void sendMessage(@Nonnull Identity source, @Nonnull Component message, @Nonnull MessageType type) {
    sender.sendMessage(source, message);
  }
}
