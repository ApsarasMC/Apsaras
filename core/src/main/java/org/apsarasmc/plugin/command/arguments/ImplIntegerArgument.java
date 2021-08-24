package org.apsarasmc.plugin.command.arguments;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apsarasmc.apsaras.command.CommandArgument;
import org.apsarasmc.apsaras.command.CommandContext;
import org.apsarasmc.apsaras.command.CommandReader;
import org.apsarasmc.apsaras.command.CommandSender;
import org.apsarasmc.apsaras.command.arguments.DoubleArgument;
import org.apsarasmc.apsaras.command.arguments.IntegerArgument;
import org.apsarasmc.apsaras.command.exception.ArgumentParseException;
import org.apsarasmc.plugin.command.ImplCommandArgument;

import java.util.Collections;
import java.util.List;

public class ImplIntegerArgument extends ImplCommandArgument<Integer> implements IntegerArgument{
  protected ImplIntegerArgument(String name, boolean optional) {
    super(name, optional);
  }
  @Override
  public void read(CommandContext context, CommandReader reader) throws ArgumentParseException {
    String value =  reader.readString();
    try {
      context.put(this, Integer.parseInt(value));
    } catch (NumberFormatException e) {
      throw new ArgumentParseException(Component.text(String.format("Can't parse integer form %s.", value), NamedTextColor.RED));
    }
  }

  @Override
  public List< String > tabComplete(CommandReader reader) {
    reader.readString();
    return Collections.emptyList();
  }

  public List< CommandArgument > requires() {
    return Collections.emptyList();
  }

  public static class Builder extends ImplCommandArgument.Builder<Integer> implements IntegerArgument.Builder {
    @Override
    public CommandArgument< Integer > build() {
      return new ImplIntegerArgument(name, optional);
    }
  }
}
