package org.apsarasmc.apsaras.command.arguments;

import org.apsarasmc.apsaras.command.CommandArgument;

public interface DoubleArgument extends CommandArgument<Double> {
  @Override
  default Class< Double > type() {
    return Double.class;
  }

  interface Builder extends CommandArgument.Builder<Double>{

  }
}
