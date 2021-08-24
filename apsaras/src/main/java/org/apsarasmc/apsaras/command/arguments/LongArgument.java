package org.apsarasmc.apsaras.command.arguments;

import org.apsarasmc.apsaras.command.CommandArgument;

public interface LongArgument extends CommandArgument<Long> {
  @Override
  default Class< Long > type() {
    return Long.class;
  }

  interface Builder extends CommandArgument.Builder<Long>{

  }
}
