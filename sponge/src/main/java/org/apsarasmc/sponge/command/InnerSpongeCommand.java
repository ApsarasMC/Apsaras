package org.apsarasmc.sponge.command;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.apsarasmc.sponge.entity.SpongePlayer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.ArgumentReader;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.EventContextKeys;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InnerSpongeCommand implements Command.Raw {
  private final org.apsarasmc.apsaras.command.Command handle;

  public InnerSpongeCommand(final org.apsarasmc.apsaras.command.Command handle) {
    this.handle = handle;
  }

  @Override
  public CommandResult process(CommandCause cause, ArgumentReader.Mutable arguments) throws CommandException {
    org.apsarasmc.apsaras.command.CommandResult commandResult;
    Optional< Audience > audienceOptional = cause.context().get(EventContextKeys.AUDIENCE);
    if(audienceOptional.isPresent() && audienceOptional.get() instanceof ServerPlayer){
      commandResult = handle.process(new SpongePlayer((ServerPlayer) audienceOptional.get()),arguments.peekString());
    }else {
      commandResult = handle.process(new InnerCommandSender(cause),arguments.peekString());
    }
    Optional<Component> errorComponent = commandResult.errorMessage();
    if(errorComponent.isPresent()){
      return CommandResult.error(errorComponent.get());
    }else {
      return CommandResult.success();
    }
  }

  @Override
  public List< CommandCompletion > complete(CommandCause cause, ArgumentReader.Mutable arguments) throws CommandException {
    Optional< Audience > audienceOptional = cause.context().get(EventContextKeys.AUDIENCE);
    List<String> completions;
    if(audienceOptional.isPresent() && audienceOptional.get() instanceof ServerPlayer){
      completions = handle.complete(new SpongePlayer((ServerPlayer) audienceOptional.get()),arguments.peekString());
    }else {
      completions = handle.complete(new InnerCommandSender(cause),arguments.peekString());
    }
    return completions.stream()
      .map(CommandCompletion::of)
      .collect(Collectors.toList());
  }

  @Override
  public boolean canExecute(CommandCause cause) {
    Optional< Audience > audienceOptional = cause.context().get(EventContextKeys.AUDIENCE);
    if(audienceOptional.isPresent() && audienceOptional.get() instanceof ServerPlayer){
      return handle.canExecute(new SpongePlayer((ServerPlayer) audienceOptional.get()));
    }else {
      return handle.canExecute(new InnerCommandSender(cause));
    }
  }

  @Override
  public Optional< Component > shortDescription(CommandCause cause) {
    return handle.shortDescription();
  }

  @Override
  public Optional< Component > extendedDescription(CommandCause cause) {
    return handle.extendedDescription();
  }

  @Override
  public Optional< Component > help(@NonNull CommandCause cause) {
    return Optional.of(Component.text("233"));
  }

  @Override
  public Component usage(CommandCause cause) {
    return handle.usage().orElse(Component.text("233"));
  }
}
