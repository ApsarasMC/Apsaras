package org.apsarasmc.apsaras.plugin;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.builder.AbstractBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface PluginUrls {
  @Nonnull
  static Builder builder() {
    return Apsaras.injector().getInstance(Builder.class);
  }

  @Nonnull
  static PluginUrls of(final @Nullable String home, final @Nullable String issue) {
    return PluginUrls.builder().home(home).issue(issue).build();
  }

  @Nullable
  String home();

  @Nullable
  String issue();

  interface Builder extends AbstractBuilder< PluginUrls > {
    Builder home(String home);

    Builder issue(String issue);
  }
}
