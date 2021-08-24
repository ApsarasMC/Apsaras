package org.apsarasmc.plugin.command.arguments;

import com.google.common.collect.ImmutableList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apsarasmc.apsaras.command.CommandArgument;
import org.apsarasmc.apsaras.command.CommandContext;
import org.apsarasmc.apsaras.command.CommandReader;
import org.apsarasmc.apsaras.command.CommandSender;
import org.apsarasmc.apsaras.command.arguments.BooleanArgument;
import org.apsarasmc.apsaras.command.exception.ArgumentParseException;
import org.apsarasmc.plugin.command.ImplCommandArgument;

import java.util.Collections;
import java.util.List;

public class ImplBooleanArgument extends ImplCommandArgument<Boolean> implements BooleanArgument {
  private final List<String> tabComplete = ImmutableList.of("false", "true");
  protected ImplBooleanArgument(String name, boolean optional) {
    super(name, optional);
  }
  @Override
  public void read(CommandContext context, CommandReader reader) throws ArgumentParseException {
    String value =  reader.readString();
    if(value.equalsIgnoreCase("true")){
      context.put(this, true);
      return;
    }else if(value.equalsIgnoreCase("false")) {
      context.put(this, false);
      return;
    }
    throw new ArgumentParseException(Component.text(String.format("Can't parse boolean form %s.", value), NamedTextColor.RED));
  }

  @Override
  public List< String > tabComplete(CommandReader reader) {
    String input = reader.readString();
    if("true".startsWith(input) && !input.isEmpty()){
      return Collections.singletonList("true");
    }
    if("false".startsWith(input) && !input.isEmpty()){
      return Collections.singletonList("false");
    }
    return tabComplete;
  }

  public List<CommandArgument> requires() {
    return Collections.emptyList();
  }

  public static class Builder extends ImplCommandArgument.Builder<Boolean> implements BooleanArgument.Builder {
    @Override
    public CommandArgument< Boolean > build() {
      return new ImplBooleanArgument(name, optional);
    }
  }
}
