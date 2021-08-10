package org.apsarasmc.plugin.command;

import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.command.CommandResult;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Optional;

public class ImplCommandResult implements CommandResult {
  private final boolean success;
  private final Component component;
  private ImplCommandResult(boolean success, Component component){
    this.success = success;
    this.component = component;

  }
  @Override
  public boolean isSuccess() {
    return this.success;
  }

  @Override
  public Optional< Component > errorMessage() {
    return Optional.ofNullable(component);
  }

  public static class Builder implements CommandResult.Builder {
    private boolean success;
    private Component component;
    @Override
    public CommandResult.Builder success() {
      this.success = true;
      this.component = null;
      return this;
    }

    @Override
    public CommandResult.Builder error(@Nullable Component errorMessage) {
      this.success = false;
      this.component = errorMessage;
      return this;
    }

    @Override
    public CommandResult build() {
      return new ImplCommandResult(this.success, this.component);
    }
  }
}
