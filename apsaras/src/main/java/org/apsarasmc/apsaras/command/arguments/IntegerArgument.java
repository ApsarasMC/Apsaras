package org.apsarasmc.apsaras.command.arguments;

import org.apsarasmc.apsaras.command.CommandArgument;

public interface IntegerArgument extends CommandArgument<Integer> {
  @Override
  default Class< Integer > type() {
    return Integer.class;
  }

  interface Builder extends CommandArgument.Builder<Integer>{

  }
}
