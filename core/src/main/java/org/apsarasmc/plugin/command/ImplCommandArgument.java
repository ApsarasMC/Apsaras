package org.apsarasmc.plugin.command;

import org.apsarasmc.apsaras.command.CommandArgument;

import javax.annotation.Nonnull;

public abstract class ImplCommandArgument<T> implements CommandArgument<T> {
  private final String name;
  private final boolean optional;

  protected ImplCommandArgument(String name, boolean optional) {
    this.name = name;
    this.optional = optional;
  }

  @Override
  public boolean optional() {
    return this.optional;
  }

  @Nonnull
  @Override
  public String name() {
    return this.name;
  }

  public static abstract class Builder<T> implements CommandArgument.Builder<T> {
    protected String name;
    protected boolean optional = false;

    @Override
    public Builder<T> name(String name) {
      this.name = name;
      return this;
    }

    @Override
    public Builder<T> optional(boolean isOptional) {
      this.optional = isOptional;
      return this;
    }
  }
}
