package org.apsarasmc.sponge.command;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.command.CommandSender;
import org.spongepowered.api.command.CommandCause;

import javax.annotation.Nonnull;

public class InnerCommandSender implements CommandSender {
  private final CommandCause handle;
  public InnerCommandSender(CommandCause handle) {
    this.handle = handle;
  }

  @Override
  public void sendMessage(@Nonnull Identity source, @Nonnull Component message, @Nonnull MessageType type) {
    handle.sendMessage(source, message);
  }
}
