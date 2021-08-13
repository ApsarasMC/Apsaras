package org.apsarasmc.plugin.command.arguments;

import com.google.common.collect.ImmutableList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apsarasmc.apsaras.command.*;
import org.apsarasmc.apsaras.command.arguments.BooleanArgument;
import org.apsarasmc.apsaras.command.exception.ArgumentParseException;
import org.apsarasmc.plugin.command.ImplCommandArgument;

import java.util.Collections;
import java.util.List;

public class ImplTwoBooleanArgument extends ImplCommandArgument<String> {
  private final CommandArgument<java.lang.Boolean> a;
  private final CommandArgument<java.lang.Boolean> b;
  private final List<CommandArgument> requires;
  protected ImplTwoBooleanArgument(String name, boolean optional) {
    super(name, optional);
    a = Arguments.BOOLEAN(name + "_a").build();
    b = Arguments.BOOLEAN(name + "_b").build();
    requires = ImmutableList.of(a,b);
  }
  @Override
  public void read(CommandContext context, CommandReader reader) throws ArgumentParseException {
    String result = "readed "+context.get(a).toString() + context.get(b).toString();
    context.sendMessage(Component.text(result));
    context.put(this, result);
  }

  @Override
  public List< String > tabComplete(CommandSender sender, CommandReader reader) {
    return null;
  }

  public List<CommandArgument> requires() {
    return requires;
  }

  public static class Builder extends ImplCommandArgument.Builder<String> {
    @Override
    public CommandArgument< String > build() {
      return new ImplTwoBooleanArgument(name, optional);
    }
  }
}
