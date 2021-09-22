package org.apsarasmc.sponge.legacy.command;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.platform.spongeapi.SpongeAudiences;
import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.Apsaras;
import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;

public class SpongeSender implements org.apsarasmc.apsaras.command.CommandSender {
  private final CommandSource sender;
  private final Audience adventure;
  public SpongeSender(CommandSource sender){
    this.sender = sender;
    adventure = Apsaras.injector().getInstance(SpongeAudiences.class).receiver(sender);
  }

  @Override
  public void sendMessage(@Nonnull Identity source, @Nonnull Component message, @Nonnull MessageType type) {
    adventure.sendMessage(source, message, type);
  }
}
