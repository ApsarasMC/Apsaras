package org.apsarasmc.plugin.command.util;

import org.apsarasmc.apsaras.command.*;
import org.apsarasmc.apsaras.command.exception.ArgumentParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdventureCommandUtil {
  private AdventureCommandUtil() {
    //
  }

  private static List<String> filterPrefix(String prefix, Iterable<String> list){
    List<String> result = new ArrayList<>();
    for (String s : list) {
      if(s.startsWith(prefix)){
        result.add(s);
      }
    }
    return result;
  }

  public static CommandResult execute(
    Command.Adventure command,
    CommandContext context,
    CommandSender sender,
    CommandReader reader
  ) throws ArgumentParseException {
    String first = reader.getString();
    Command.Adventure subcommand = command.subcommands().get(first);
    if(subcommand != null){
      reader.readString();
      return execute(subcommand, context, sender, reader);
    }
    read(context, reader, command.arguments());
    return command.execute(context);
  }

  private static void read(CommandContext context, CommandReader reader, List< CommandArgument > arguments) throws ArgumentParseException {
    for (CommandArgument argument : arguments) {
      read(context, reader, argument.requires());
      argument.read(context, reader);
    }
  }

  public static List<String> complete(
    Command.Adventure command,
    CommandSender sender,
    CommandReader reader
  ) {
    String first = reader.getString();
    int remain = reader.remain();
    Command.Adventure subcommand = command.subcommands().get(first);
    if(subcommand != null){
      reader.readString();
      return complete(subcommand, sender, reader);
    }

    List<String> complete = tab(reader, command.arguments());
    if(complete == null || !reader.ended()){
      complete = Collections.emptyList();
    }

    if(first.length() == remain) {
      complete = new ArrayList<>(complete);
      complete.addAll(filterPrefix(first, command.subcommands().keySet()));
    }
    return complete;
  }

  private static List<String> tab(CommandReader reader, List< CommandArgument > arguments) {
    List<String> complete = null;
    List<String> pComplete = null;
    for (CommandArgument argument : arguments) {
      pComplete = tab(reader,argument.requires());
      if (pComplete != null) {
        complete = pComplete;
      }
      if(reader.ended()){
        return complete;
      }
      pComplete = argument.tabComplete(reader);
      if (pComplete != null) {
        complete = pComplete;
      }
      if(reader.ended()){
        return complete;
      }
    }
    return complete;
  }
}
