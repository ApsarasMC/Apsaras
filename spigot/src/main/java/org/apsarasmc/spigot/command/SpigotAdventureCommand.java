package org.apsarasmc.spigot.command;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.command.*;
import org.apsarasmc.apsaras.command.exception.ArgumentParseException;
import org.apsarasmc.plugin.command.ImplCommandContext;
import org.apsarasmc.plugin.command.ImplCommandReader;
import org.apsarasmc.plugin.command.util.AdventureCommandUtil;
import org.apsarasmc.spigot.command.util.CommandSenderUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SpigotAdventureCommand extends org.bukkit.command.Command{
  private final Command.Adventure handle;

  public SpigotAdventureCommand(@NotNull String name, Command.Adventure handle) {
    super(name);
    this.handle = handle;
  }

  @Override
  public boolean execute(@NotNull org.bukkit.command.CommandSender spigotSender, @NotNull String commandLabel, @NotNull String[] args) {
    try {
      CommandSender sender = CommandSenderUtil.transfer(spigotSender);
      CommandReader reader = new ImplCommandReader(String.join(" ",args));
      CommandContext context = new ImplCommandContext(sender, new HashMap<>());
      return AdventureCommandUtil.execute(handle, context, sender, reader).isSuccess();
    }catch (ArgumentParseException e) {
      Apsaras.server().logger().info("Failed to invoke command.", e);
    }
    return false;
  }

  @NotNull
  @Override
  public List< String > tabComplete(@NotNull org.bukkit.command.CommandSender spigotSender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
    CommandSender sender = CommandSenderUtil.transfer(spigotSender);
    CommandReader reader = new ImplCommandReader(String.join(" ", args));
    return AdventureCommandUtil.complete(handle, sender, reader);
  }
}
