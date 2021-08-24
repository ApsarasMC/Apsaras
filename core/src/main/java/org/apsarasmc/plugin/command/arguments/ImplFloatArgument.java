package org.apsarasmc.plugin.command.arguments;

import com.google.common.collect.ImmutableList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apsarasmc.apsaras.command.CommandArgument;
import org.apsarasmc.apsaras.command.CommandContext;
import org.apsarasmc.apsaras.command.CommandReader;
import org.apsarasmc.apsaras.command.CommandSender;
import org.apsarasmc.apsaras.command.arguments.DoubleArgument;
import org.apsarasmc.apsaras.command.arguments.FloatArgument;
import org.apsarasmc.apsaras.command.exception.ArgumentParseException;
import org.apsarasmc.plugin.command.ImplCommandArgument;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ImplFloatArgument extends ImplCommandArgument<Float> implements FloatArgument{
  protected ImplFloatArgument(String name, boolean optional) {
    super(name, optional);
  }
  @Override
  public void read(CommandContext context, CommandReader reader) throws ArgumentParseException {
    String value =  reader.readString();
    try {
      context.put(this, Float.parseFloat(value));
    } catch (NumberFormatException e) {
      throw new ArgumentParseException(Component.text(String.format("Can't parse float form %s.", value), NamedTextColor.RED));
    }
  }

  @Override
  public List< String > tabComplete(CommandReader reader) {
    String sub = reader.readString();
    return ImmutableList.of("aaa","bbb").stream().filter(s -> s.startsWith(sub)).collect(Collectors.toList());
  }

  public List< CommandArgument > requires() {
    return Collections.emptyList();
  }

  public static class Builder extends ImplCommandArgument.Builder<Float> implements FloatArgument.Builder {
    @Override
    public CommandArgument< Float > build() {
      return new ImplFloatArgument(name, optional);
    }
  }
}
