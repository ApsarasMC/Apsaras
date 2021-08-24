package org.apsarasmc.sponge.command;

import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.command.Command;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.ArgumentReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpongeRawCommand implements org.spongepowered.api.command.Command.Raw {
  private final Command.Raw handle;

  public SpongeRawCommand(Command.Raw handle) {
    this.handle = handle;
  }

  @Override
  public CommandResult process(CommandCause cause, ArgumentReader.Mutable arguments) throws CommandException {
    return SpongeCommandUtil.transfer(handle.process(SpongeCommandUtil.sender(cause), arguments.input()));
  }

  @Override
  public List< CommandCompletion > complete(CommandCause cause, ArgumentReader.Mutable arguments) throws CommandException {
    List< CommandCompletion > completions = new ArrayList<>();
    for (String s : handle.complete(SpongeCommandUtil.sender(cause), arguments.input())) {
      completions.add(CommandCompletion.of(s));
    }
    return completions;
  }

  @Override
  public boolean canExecute(CommandCause cause) {
    return handle.canExecute(SpongeCommandUtil.sender(cause));
  }

  @Override
  public Optional< Component > shortDescription(CommandCause cause) {
    return handle.shortDescription(SpongeCommandUtil.sender(cause));
  }

  @Override
  public Optional< Component > extendedDescription(CommandCause cause) {
    return handle.extendedDescription(SpongeCommandUtil.sender(cause));
  }

  @Override
  public Component usage(CommandCause cause) {
    return handle.usage(SpongeCommandUtil.sender(cause)).orElse(Component.empty());
  }
}
