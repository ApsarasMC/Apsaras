package org.apsarasmc.apsaras.command;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.builder.AbstractBuilder;

public interface Checkable {
  Checkable TRUE = context -> true;
  Checkable FALSE = context -> false;

  boolean check(CommandSender sender);

  static Builder builder() {
    return Apsaras.injector().getInstance(Builder.class);
  }
  interface Builder extends AbstractBuilder<Checkable>{
    Builder add(Checkable checker);
  }
}
