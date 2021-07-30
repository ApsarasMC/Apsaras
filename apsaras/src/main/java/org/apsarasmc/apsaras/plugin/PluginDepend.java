package org.apsarasmc.apsaras.plugin;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.builder.AbstractBuilder;
import org.apsarasmc.apsaras.builder.NamedBuilder;
import org.apsarasmc.apsaras.util.Named;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface PluginDepend extends Named {
  @Nonnull
  static PluginDepend of(final @Nullable Type type, final @Nullable String name) {
    return PluginDepend.builder().type(type).name(name).build();
  }

  @Nonnull
  static Builder builder() {
    return Apsaras.injector().getInstance(Builder.class);
  }

  @Nonnull
  Type type();

  enum Type {
    REQUIRE,
    SOFT,
    COOPERATE,
    LIBRARY
  }

  interface Builder extends AbstractBuilder< PluginDepend >,
    NamedBuilder< PluginDepend, PluginDepend.Builder > {
    Builder type(Type type);
  }
}
