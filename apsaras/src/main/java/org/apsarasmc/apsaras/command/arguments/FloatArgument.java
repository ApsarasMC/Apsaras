package org.apsarasmc.apsaras.command.arguments;

import org.apsarasmc.apsaras.command.CommandArgument;

public interface FloatArgument extends CommandArgument<Float> {
  @Override
  default Class< Float > type(){
    return Float.class;
  }

  interface Builder extends CommandArgument.Builder<Float>{

  }
}
