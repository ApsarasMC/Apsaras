package org.apsarasmc.plugin.command.arguments;

import com.google.common.collect.ImmutableList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apsarasmc.apsaras.command.CommandArgument;
import org.apsarasmc.apsaras.command.CommandContext;
import org.apsarasmc.apsaras.command.CommandReader;
import org.apsarasmc.apsaras.command.CommandSender;
import org.apsarasmc.apsaras.command.arguments.BooleanArgument;
import org.apsarasmc.apsaras.command.arguments.StringArgument;
import org.apsarasmc.apsaras.command.exception.ArgumentParseException;
import org.apsarasmc.plugin.command.ImplCommandArgument;

import java.util.Collections;
import java.util.List;

public class ImplStringArgument extends ImplCommandArgument<String> implements StringArgument {
  protected ImplStringArgument(String name, boolean optional) {
    super(name, optional);
  }
  @Override
  public void read(CommandContext context, CommandReader reader) throws ArgumentParseException {
    String value =  reader.readString();
    context.put(this, value);
  }

  @Override
  public List< String > tabComplete(CommandReader reader) {
    reader.readString();
    return Collections.emptyList();
  }

  public List<CommandArgument> requires() {
    return Collections.emptyList();
  }

  public static class Builder extends ImplCommandArgument.Builder<String> implements StringArgument.Builder {
    @Override
    public CommandArgument< String > build() {
      return new ImplStringArgument(name, optional);
    }
  }
}
