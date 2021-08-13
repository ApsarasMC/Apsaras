package org.apsarasmc.plugin.command;

import org.apsarasmc.apsaras.command.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImplCommand implements Command.Adventure {
  private final Executable executor;
  private final Checkable checker;
  private final List<CommandArgument> argumentList;

  public ImplCommand(Executable executor, Checkable checker, List< CommandArgument > argumentList) {
    this.executor = executor;
    this.checker = checker;
    this.argumentList = argumentList;
  }

  @Override
  public boolean canExecute(CommandSender sender) {
    return this.checker.check(sender);
  }

  @Override
  public CommandResult execute(CommandContext context) {
    return this.executor.execute(context);
  }

  @Override
  public List< CommandArgument > arguments() {
    return argumentList;
  }

  public static class Builder implements Command.Builder {
    private Executable executor;
    private final Checkable.Builder checkableBuilder = Checkable.builder();
    private final List<CommandArgument> argumentList = new ArrayList<>();

    @Override
    public Adventure build() {
      return new ImplCommand(executor, checkableBuilder.build(), argumentList);
    }

    @Override
    public Command.Builder append(CommandArgument< ? > argument) {
      argumentList.add(argument);
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
