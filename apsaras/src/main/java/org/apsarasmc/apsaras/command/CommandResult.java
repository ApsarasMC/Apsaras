package org.apsarasmc.apsaras.command;

import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.builder.AbstractBuilder;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Optional;

public interface CommandResult {
  static Builder builder() {
    return Apsaras.injector().getInstance(CommandResult.Builder.class);
  }

  static CommandResult success() {
    return CommandResult.builder().success().build();
  }
  static CommandResult error(final Component errorMessage) {
    return CommandResult.builder().error(errorMessage).build();
  }

  boolean isSuccess();
  Optional< Component > errorMessage();

  interface Builder extends AbstractBuilder< CommandResult > {
    Builder success();
    Builder error(@Nullable Component errorMessage);

    @Override
    CommandResult build();

  }
}