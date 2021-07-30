package org.apsarasmc.apsaras.plugin;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.builder.AbstractBuilder;
import org.apsarasmc.apsaras.builder.NamedBuilder;
import org.apsarasmc.apsaras.util.Named;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public interface PluginMeta extends Named {
  @Nonnull
  static Builder builder() {
    return Apsaras.injector().getInstance(Builder.class);
  }

  @Nullable
  String describe();

  @Nullable
  String version();

  @Nullable
  PluginUrls urls();

  @Nullable
  String main();

  @Nonnull
  Collection< PluginDepend > depends();

  interface Builder extends AbstractBuilder< PluginMeta >,
    NamedBuilder< PluginMeta, Builder > {
    Builder describe(String describe);

    Builder version(String version);

    Builder urls(PluginUrls urls);

    Builder main(String main);

    Builder depend(PluginDepend depend);

    default Builder urls(PluginUrls.Builder urlsBuilder) {
      return this.urls(urlsBuilder.build());
    }

    default Builder depend(PluginDepend.Builder dependBuilder) {
      return this.depend(dependBuilder.build());
    }
  }
}
