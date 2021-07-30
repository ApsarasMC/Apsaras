package org.apsarasmc.apsaras.util;

import net.kyori.adventure.key.Key;
import org.apsarasmc.apsaras.Apsaras;

import javax.annotation.Nonnull;

public interface ResourceKey extends Key {
  String BRIGADIER_NAMESPACE = "brigadier";
  String MINECRAFT_NAMESPACE = "minecraft";
  String APSARAS_NAMESPACE = "apsaras";

  @Nonnull
  static ResourceKey brigadier(final @Nonnull String value) {
    return ResourceKey.of(ResourceKey.BRIGADIER_NAMESPACE, value);
  }

  @Nonnull
  static ResourceKey minecraft(final @Nonnull String value) {
    return ResourceKey.of(ResourceKey.MINECRAFT_NAMESPACE, value);
  }

  @Nonnull
  static ResourceKey apsaras(final @Nonnull String value) {
    return ResourceKey.of(ResourceKey.APSARAS_NAMESPACE, value);
  }

  @Nonnull
  static Factory factory() {
    return Apsaras.injector().getInstance(Factory.class);
  }

  @Nonnull
  static ResourceKey of(final @Nonnull String namespace, final @Nonnull String value) {
    return ResourceKey.factory().of(namespace, value);
  }

  interface Factory {
    ResourceKey of(String namespace, String value);

    ResourceKey resolve(String formatted);
  }
}
