package org.apsarasmc.sponge.legacy.command;

import org.apsarasmc.apsaras.command.Command;
import org.apsarasmc.apsaras.command.CommandSender;
import org.apsarasmc.sponge.legacy.command.util.CommandSenderUtil;
import org.apsarasmc.sponge.legacy.command.util.SpongeCommandUtil;
import org.apsarasmc.sponge.legacy.util.TextComponentUtil;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class SpongeRawCommand implements CommandCallable {
  private final Command.Raw handle;
  private final String name;
  public SpongeRawCommand( String name, Command.Raw handle) {
    this.name=name;
    this.handle = handle;
  }

  @Override
  public CommandResult process(CommandSource source, String arguments) throws CommandException {
    CommandSender sender = CommandSenderUtil.transfer(source);
    return SpongeCommandUtil.transfer(handle.process(sender,arguments));
  }

  @Override
  public List< String > getSuggestions(CommandSource source, String arguments, @Nullable Location< World > targetPosition) throws CommandException {
    return handle.complete(CommandSenderUtil.transfer(source), arguments);
  }

  @Override
  public boolean testPermission(CommandSource source) {
    return true;
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
