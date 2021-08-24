package org.apsarasmc.apsaras.command;

import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.builder.AbstractBuilder;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Command {
  boolean canExecute(CommandSender sender);

  default Optional< Component > shortDescription(CommandSender sender) {
    return Optional.empty();
  }

  default Optional< Component > extendedDescription(CommandSender sender) {
    return Optional.empty();
  }

  default Optional<Component> help(CommandSender sender) {
    final Optional<Component> shortDesc = this.shortDescription(sender);
    final Optional<Component> extended = this.extendedDescription(sender);
    if (extended.isPresent()) {
      if (shortDesc.isPresent()) {
        return Optional.of(Component.text().append(shortDesc.get(), Component.newline(), Component.newline(), extended.get()).build());
      } else {
        return extended;
      }
    }
    return shortDesc;
  }

  default Optional< Component > usage(CommandSender sender){
    return Optional.empty();
  }

  static Command.Builder builder(){
    return Apsaras.injector().getInstance(Command.Builder.class);
  }

  interface Raw extends Command {
    CommandResult process(CommandSender sender, String argument);

    List<String> complete(CommandSender sender, String argument);
  }

  interface Adventure extends Command {
    CommandResult execute(CommandContext context);
    List<CommandArgument> arguments();
    Map<String, Command.Adventure> subcommands();
  }

  interface Builder extends AbstractBuilder<Command.Adventure> {
    Builder append(CommandArgument<?> argument);
    Builder subcommand(String name, Command.Adventure command);
    Builder checker(Checkable checker);
    Builder executor(Executable executor);
  }
}