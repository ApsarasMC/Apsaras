package org.apsarasmc.apsaras.command.arguments;

import org.apsarasmc.apsaras.command.CommandArgument;

public interface StringArgument extends CommandArgument<String> {
  @Override
  default Class< String > type() {
    return String.class;
  }

  interface Builder extends CommandArgument.Builder<String>{

  }
}
