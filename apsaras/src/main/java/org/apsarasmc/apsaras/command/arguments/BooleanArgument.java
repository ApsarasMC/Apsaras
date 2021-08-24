package org.apsarasmc.apsaras.command.arguments;

import org.apsarasmc.apsaras.command.CommandArgument;

public interface BooleanArgument extends CommandArgument<Boolean> {
  @Override
  default Class< Boolean > type() {
    return Boolean.class;
  }

  interface Builder extends CommandArgument.Builder<Boolean> {

  }
}