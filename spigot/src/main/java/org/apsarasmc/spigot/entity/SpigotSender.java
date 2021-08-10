package org.apsarasmc.spigot.entity;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import org.apsarasmc.spigot.util.TextComponentUtil;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class SpigotSender implements org.apsarasmc.apsaras.command.CommandSender {
  private final CommandSender sender;
  public SpigotSender(CommandSender sender){
    this.sender = sender;
  }

  @Override
  public void sendMessage(@Nonnull Identity source, @Nonnull Component message, @Nonnull MessageType type) {
    sender.spigot().sendMessage(source.uuid(), TextComponentUtil.toBukkit(message));
  }
}
