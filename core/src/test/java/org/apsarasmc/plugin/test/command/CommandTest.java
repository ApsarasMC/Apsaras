package org.apsarasmc.plugin.test.command;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.command.*;
import org.apsarasmc.apsaras.command.arguments.BooleanArgument;
import org.apsarasmc.apsaras.command.exception.ArgumentParseException;
import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.plugin.command.ImplCommandContext;
import org.apsarasmc.plugin.command.ImplCommandReader;
import org.apsarasmc.plugin.command.arguments.ImplTwoBooleanArgument;
import org.apsarasmc.plugin.test.InjectTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@InjectTest
public class CommandTest {
  @Inject
  Player player;
  @Test
  void a() throws ArgumentParseException {
    test("true true ");
  }
  void test(String test) {
    CommandArgument<String> argument = new ImplTwoBooleanArgument.Builder().name("aa").build();
    CommandArgument<String> sss = Arguments.STRING("aaa").build();
    Command.Adventure command = Command.builder()
      .append(argument)
      .append(sss)
      .build();

    CommandReader reader = new ImplCommandReader(test);
    List<String> complete = tab(reader, command.arguments());
    if(reader.remain() > 0){
      complete = Collections.emptyList();
    }else if(reader.remain() == 0 && !reader.ended()){
      complete = Collections.emptyList();
    }
    Apsaras.server().logger().info("tab result: {}", complete);

    try {
      reader = new ImplCommandReader(test);
      CommandContext context = new ImplCommandContext(player, new HashMap<>());
      read(context,reader,command.arguments());
      Apsaras.server().logger().info("invoke result: {}",context.get(argument));
    }catch (ArgumentParseException e) {
      Apsaras.server().logger().info("Failed to invoke", e);
    }
  }

  List<String> tab(CommandReader reader, List<CommandArgument> arguments) {
    List<String> pComplete = null;
    List<String> complete = null;
    boolean returnFirst = false;
    for (CommandArgument argument : arguments) {
      if(returnFirst){
        return Collections.emptyList();
      }
      if(reader.remain() <= 0){
        if(reader.ended()) {
          return complete;
        }else {
          returnFirst = true;
        }
      }
      pComplete = tab(reader,argument.requires());
      if (pComplete != null) {
        complete = pComplete;
        if(returnFirst){
          return complete;
        }
      }
      pComplete = argument.tabComplete(player, reader);
      if (pComplete != null) {
        complete = pComplete;
        if(returnFirst){
          return complete;
        }
      }
    }
    return complete;
  }

  void read(CommandContext context, CommandReader reader, List<CommandArgument> arguments) throws ArgumentParseException {
    for (CommandArgument argument : arguments) {
      read(context,reader,argument.requires());
      argument.read(context, reader);
    }
  }
}
