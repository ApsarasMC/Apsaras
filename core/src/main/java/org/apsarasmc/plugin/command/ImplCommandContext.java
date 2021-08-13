package org.apsarasmc.plugin.command;

import org.apsarasmc.apsaras.command.CommandArgument;
import org.apsarasmc.apsaras.command.CommandContext;
import org.apsarasmc.apsaras.command.CommandSender;
import org.apsarasmc.apsaras.entity.PermissionCheckable;

import java.util.HashMap;
import java.util.Map;

public class ImplCommandContext implements CommandContext {
  private final CommandSender sender;
  private final Map<CommandArgument<?>, Object> arguments;

  public ImplCommandContext(CommandSender sender, Map< CommandArgument< ? >, Object > arguments) {
    this.sender = sender;
    this.arguments = arguments;
  }

  @Override
  public CommandSender sender() {
    return sender;
  }

  @Override
  @SuppressWarnings("unchecked")
  public < T > T get(CommandArgument< T > argument) {
    return (T) arguments.get(argument);
  }

  @Override
  public < T > void put(CommandArgument< T > argument, T value) {
    arguments.put(argument,value);
  }

  @Override
  public boolean hasPermission(String name) {
    if(sender instanceof PermissionCheckable){
      return ((PermissionCheckable) sender).hasPermission(name);
    }
    return true;
  }

  public static class Builder implements CommandContext.Builder {
    private CommandSender sender;
    private final Map<CommandArgument<?>, Object> arguments = new HashMap<>();

    @Override
    public CommandContext build() {
      return new ImplCommandContext(sender, arguments);
    }

    @Override
    public CommandContext.Builder sender(CommandSender sender) {
      this.sender = sender;
      return this;
    }

    @Override
    public < T > CommandContext.Builder argument(CommandArgument< T > argument, T value) {
      this.arguments.put(argument, value);
      return this;
    }
  }
}
