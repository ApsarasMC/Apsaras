package org.apsarasmc.apsaras.command;

import org.apsarasmc.apsaras.builder.AbstractBuilder;
import org.apsarasmc.apsaras.builder.NamedBuilder;
import org.apsarasmc.apsaras.command.exception.ArgumentParseException;
import org.apsarasmc.apsaras.util.Named;

import java.util.List;

public interface CommandArgument<T> extends Named {
  void read(CommandContext context ,CommandReader reader) throws ArgumentParseException;
  List<String> tabComplete(CommandSender sender, CommandReader reader);
  boolean optional();
  List<CommandArgument> requires();
  interface Builder<T> extends AbstractBuilder<CommandArgument<T>>, NamedBuilder<CommandArgument<T>, Builder<T> > {
    Builder<T> optional(boolean isOptional);
  }
}
