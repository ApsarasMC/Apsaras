package org.apsarasmc.plugin.util;

import org.apsarasmc.apsaras.util.ResourceKey;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ImplResourceKey implements ResourceKey {
  private final @Nonnull
  String namespace;
  private final @Nonnull
  String value;

  private ImplResourceKey(final @Nonnull String namespace, final @Nonnull String value) {
    this.namespace = namespace;
    this.value = value;
  }

  @Override
  public @Nonnull
  String namespace() {
    return this.namespace;
  }

  @Override
  public @Nonnull
  String value() {
    return this.value;
  }

  @Override
  public @Nonnull
  String asString() {
    return this.namespace() + ":" + this.value();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ImplResourceKey that = (ImplResourceKey) o;
    return namespace.equals(that.namespace) && value.equals(that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(namespace, value);
  }

  public static final class Factory implements ResourceKey.Factory {
    private final Map< ImplResourceKey, ImplResourceKey > resourceKeyMap = new ConcurrentHashMap<>();

    public ResourceKey of(final @Nonnull String namespace, final @Nonnull String value) {
      Objects.requireNonNull(namespace, "namespace");
      Objects.requireNonNull(value, "value");
      ImplResourceKey key = new ImplResourceKey(namespace, value);
      return resourceKeyMap.computeIfAbsent(key, pkey -> key);
    }

    @Override
    public ResourceKey resolve(final @Nonnull String formatted) {
      Objects.requireNonNull(formatted, "formatted");
      String[] parts = formatted.split(":");
      if (parts.length != 2) {
        throw new IllegalArgumentException("ResourceKey formatted should be likes a:b, " + formatted + " inputted.");
      }
      return this.of(parts[ 0 ], parts[ 1 ]);
    }
  }
}
