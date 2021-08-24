package org.apsarasmc.sponge.command;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.command.CommandArgument;
import org.apsarasmc.apsaras.command.CommandContext;
import org.apsarasmc.apsaras.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.parameter.Parameter;

import java.util.Map;

public class SpongeCommandContextBuilder implements CommandContext {
  private final org.spongepowered.api.command.parameter.CommandContext.Builder commandContext;
  private final Map<String, Parameter.Value > argumentMap;
  private final CommandSender commandSender;

  public SpongeCommandContextBuilder(org.spongepowered.api.command.parameter.CommandContext.Builder commandContext,
                                     Map< String, Parameter.Value > argumentMap,
                                     CommandSender commandSender) {
    this.commandContext = commandContext;
    this.argumentMap = argumentMap;
    this.commandSender = commandSender;
  }

  @Override
  public CommandSender sender() {
    return commandSender;
  }

  @Override
  public < T > T get(CommandArgument< T > argument) {
    return (T) commandContext.requireOne(argumentMap.get(argument.name()));
  }

  @Override
  public < T > void put(CommandArgument< T > argument, T value) {
    commandContext.putEntry(argumentMap.get(argument.name()), value);
  }

  @Override
  public boolean hasPermission(String name) {
    return commandContext.hasPermission(name);
  }

  @Override
  public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
    commandContext.sendMessage(source, message);
  }
}
