package org.apsarasmc.apsaras.command;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.command.arguments.*;

public interface Arguments {
  static CommandArgument.Builder<Boolean> BOOLEAN(String name) {
    return Apsaras.injector().getInstance(BooleanArgument.Builder.class).name(name);
  }

  static CommandArgument.Builder<Double> DOUBLE(String name) {
    return Apsaras.injector().getInstance(DoubleArgument.Builder.class).name(name);
  }

  static CommandArgument.Builder<Float> FLOAT(String name) {
    return Apsaras.injector().getInstance(FloatArgument.Builder.class).name(name);
  }

  static CommandArgument.Builder<Integer> INTEGER(String name) {
    return Apsaras.injector().getInstance(IntegerArgument.Builder.class).name(name);
  }

  static CommandArgument.Builder<Long> LONG(String name) {
    return Apsaras.injector().getInstance(LongArgument.Builder.class).name(name);
  }

  static CommandArgument.Builder<String> STRING(String name) {
    return Apsaras.injector().getInstance(StringArgument.Builder.class).name(name);
  }
}
