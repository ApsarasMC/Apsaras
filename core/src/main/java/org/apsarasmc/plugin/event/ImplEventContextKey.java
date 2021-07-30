package org.apsarasmc.plugin.event;

import org.apsarasmc.apsaras.event.EventContextKey;
import org.apsarasmc.apsaras.util.ResourceKey;

import java.util.Objects;

public class ImplEventContextKey< T > implements EventContextKey< T > {
  private Class< T > type;
  private ResourceKey key;

  private ImplEventContextKey(final Class< T > type, final ResourceKey key) {
    this.type = type;
    this.key = key;
  }

  @Override
  public Class< T > allowedType() {
    return type;
  }

  @Override
  public boolean isInstance(Object value) {
    return type.isAssignableFrom(value.getClass());
  }

  @Override
  @SuppressWarnings ("unchecked")
  public T cast(Object value) {
    return (T) value;
  }

  @Override
  public ResourceKey key() {
    return key;
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj != null
      && ImplEventContextKey.class.equals(obj.getClass())
      && type.equals(obj.getClass())
      && key.equals(((ImplEventContextKey< ? >) obj).key);
  }

  public static class Builder< T > implements EventContextKey.Builder< T > {
    private Class< T > type;
    private ResourceKey key;

    @Override
    public EventContextKey< T > build() {
      Objects.requireNonNull(type, "type");
      Objects.requireNonNull(key, "key");
      return new ImplEventContextKey<>(type, key);
    }

    @Override
    public EventContextKey.Builder< T > key(ResourceKey key) {
      Objects.requireNonNull(key, "key");
      this.key = key;
      return this;
    }

    @Override
    public < N > EventContextKey.Builder< N > type(Class< N > type) {
      Objects.requireNonNull(type, "type");
      Builder< N > newBuilder = new Builder<>();
      newBuilder.type = type;
      if (key != null) {
        newBuilder.key = key;
      }
      return newBuilder;
    }
  }
}
