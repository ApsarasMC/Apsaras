package org.apsarasmc.plugin.command;

import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.command.*;

import java.util.*;

public class ImplCommand implements Command.Adventure {
  private final Executable executor;
  private final Checkable checker;
  private final List<CommandArgument> arguments;
  private final Map<String, Adventure> subcommands;

  public ImplCommand(Executable executor, Checkable checker, List< CommandArgument > arguments, Map<String, Adventure> subcommands) {
    this.executor = executor;
    this.checker = checker;
    this.arguments = arguments;
    this.subcommands = subcommands;
  }

  @Override
  public boolean canExecute(CommandSender sender) {
    return this.checker.check(sender);
  }

  @Override
  public CommandResult execute(CommandContext context) {
    if(this.executor == null){
      context.sendMessage(usage(context.sender()).orElse(Component.empty()));
      return CommandResult.error(Component.empty());
    }
    return this.executor.execute(context);
  }

  @Override
  public List< CommandArgument > arguments() {
    return arguments;
  }

  @Override
  public Map<String, Adventure> subcommands() {
    return subcommands;
  }

  public static class Builder implements Command.Builder {
    private Executable executor;
    private final Checkable.Builder checkableBuilder = Checkable.builder();
    private final List<CommandArgument> arguments = new ArrayList<>();
    private final Map<String, Adventure> subcommands = new HashMap<>();

    @Override
    public Adventure build() {
      return new ImplCommand(executor, checkableBuilder.build(), arguments, subcommands);
    }

    @Override
    public Command.Builder append(CommandArgument< ? > argument) {
      arguments.add(argument);
      return this;
    }

    @Override
    public Command.Builder subcommand(String name, Command.Adventure command){
      subcommands.put(name,command);
      return this;
    }

    @Override
    public Command.Builder checker(Checkable checker) {
      Objects.requireNonNull(checker, "checker");
      checkableBuilder.add(checker);
      return this;
    }

    @Override
    public Command.Builder executor(Executable executor) {
      Objects.requireNonNull(executor, "executor");
      this.executor = executor;
      return this;
    }
  }
}
