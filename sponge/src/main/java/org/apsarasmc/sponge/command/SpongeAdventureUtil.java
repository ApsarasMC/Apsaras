package org.apsarasmc.sponge.command;

import org.apsarasmc.apsaras.command.Arguments;
import org.apsarasmc.apsaras.command.Checkers;
import org.apsarasmc.apsaras.command.CommandArgument;
import org.apsarasmc.apsaras.command.arguments.*;
import org.apsarasmc.plugin.command.ImplCommandContext;
import org.apsarasmc.plugin.command.ImplCommandReader;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.K;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.exception.ArgumentParseException;
import org.spongepowered.api.command.parameter.ArgumentReader;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.command.parameter.managed.ValueCompleter;
import org.spongepowered.api.command.parameter.managed.ValueParameterModifier;
import org.spongepowered.api.command.parameter.managed.ValueParser;
import org.spongepowered.api.command.parameter.managed.ValueUsage;

import java.util.*;
import java.util.function.Predicate;

public class SpongeAdventureUtil {
  private SpongeAdventureUtil() {
    //
  }

  public static Command.Parameterized transfer(org.apsarasmc.apsaras.command.Command.Adventure adventure){
    Command.Builder builder = Command.builder();
    adventure.subcommands().forEach((k,v)->{
      builder.addChild(transfer(v),k);

    });
    Map<String, Parameter.Value > argumentMap = new HashMap<>();
    read(argumentMap, builder, adventure.arguments());
    builder.executor(context -> SpongeCommandUtil.transfer(
      adventure.execute(
        new SpongeCommandContext(
          context,
          argumentMap,
          SpongeCommandUtil.sender(context.cause())
        )
      )
    ));
    builder.shortDescription(adventure.extendedDescription(null).orElse(null));
    builder.extendedDescription(adventure.extendedDescription(null).orElse(null));
    builder.executionRequirements(cause -> adventure.canExecute(SpongeCommandUtil.sender(cause)));
    return builder.build();
  }

  private static void read(Map<String, Parameter.Value > argumentMap, Command.Builder builder, List< CommandArgument > arguments){
    for (CommandArgument argument : arguments) {
      read(argumentMap, builder, argument.requires());
      builder.addParameter(transfer(argumentMap, argument));
    }
  }

  protected static <T> Parameter transfer(Map<String, Parameter.Value > argumentMap, CommandArgument<T> argument){
    Parameter.Value.Builder builder = null;
    if(argument instanceof BooleanArgument) {
      builder = Parameter.bool();
    }else if (argument instanceof DoubleArgument) {
      builder = Parameter.doubleNumber();
    }else if (argument instanceof IntegerArgument) {
      builder = Parameter.integerNumber();
    }else if (argument instanceof LongArgument) {
      builder = Parameter.longNumber();
    }else if (argument instanceof StringArgument) {
      builder = Parameter.string();
    }else {
      builder = Parameter.builder(argument.type()).key(argument.name());
      builder.addParser((parameterKey, reader, context) -> {
        try {
          argument.read(new SpongeCommandContextBuilder(context, argumentMap, SpongeCommandUtil.sender(context.cause())), new SpongeCommandReader(reader));
        } catch (org.apsarasmc.apsaras.command.exception.ArgumentParseException e) {
          throw new ArgumentParseException(e.componentMessage(), reader.input(), reader.cursor());
        }
        return context.one(argumentMap.get(argument.name()));
      });
      builder.completer((context, currentInput) -> {
        List<CommandCompletion> completions = new ArrayList<>();
        for (String s : argument.tabComplete(new ImplCommandReader(currentInput))) {
          completions.add(CommandCompletion.of(s));
        }
        return completions;
      });
    }
    if(argument.optional()){
      builder.optional();
    }
    builder.key(argument.name());
    return builder.build();
  }
}
