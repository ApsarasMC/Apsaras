package org.apsarasmc.sponge.legacy.command;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.command.Command;
import org.apsarasmc.apsaras.command.CommandContext;
import org.apsarasmc.apsaras.command.CommandReader;
import org.apsarasmc.apsaras.command.CommandSender;
import org.apsarasmc.apsaras.command.exception.ArgumentParseException;
import org.apsarasmc.plugin.command.ImplCommandContext;
import org.apsarasmc.plugin.command.ImplCommandReader;
import org.apsarasmc.plugin.command.util.AdventureCommandUtil;
import org.apsarasmc.sponge.legacy.command.util.CommandSenderUtil;
import org.apsarasmc.sponge.legacy.command.util.SpongeCommandUtil;
import org.apsarasmc.sponge.legacy.util.TextComponentUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SpongeAdventureCommand implements CommandCallable {
  private final Command.Adventure handle;
  private final String name;

  public SpongeAdventureCommand(@NotNull String name, Command.Adventure handle) {
    this.handle = handle;
    this.name = name;
  }

  @Override
  public CommandResult process(CommandSource source, String arguments) throws CommandException {
    try {
      CommandSender sender = CommandSenderUtil.transfer(source);
      CommandReader reader = new ImplCommandReader(arguments);
      CommandContext context = new ImplCommandContext(sender, new HashMap<>());
      return SpongeCommandUtil.transfer(AdventureCommandUtil.execute(handle, context, sender, reader));
    }catch (ArgumentParseException e) {
      Apsaras.server().logger().info("Failed to invoke command.", e);
      throw new CommandException(TextComponentUtil.toSponge(e.componentMessage()),e);
    }
  }

  @Override
  public List< String > getSuggestions(CommandSource source, String arguments, @Nullable Location< World > targetPosition) throws CommandException {
    CommandSender sender = CommandSenderUtil.transfer(source);
    CommandReader reader = new ImplCommandReader(arguments);
    return AdventureCommandUtil.complete(handle, sender, reader);
  }

  @Override
  public boolean testPermission(CommandSource source) {
    return handle.canExecute(CommandSenderUtil.transfer(source));
  }

  @Override
  public Optional< Text > getShortDescription(CommandSource source) {
    return TextComponentUtil.toSponge(handle.shortDescription(CommandSenderUtil.transfer(source)));
  }

  @Override
  public Optional< Text > getHelp(CommandSource source) {
    return TextComponentUtil.toSponge(handle.extendedDescription(CommandSenderUtil.transfer(source)));
  }

  @Override
  public Text getUsage(CommandSource source) {
    return TextComponentUtil.toSponge(handle.usage(CommandSenderUtil.transfer(source))).orElseGet(()->Text.of(name));
  }
}