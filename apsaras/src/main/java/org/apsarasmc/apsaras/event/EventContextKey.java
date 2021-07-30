package org.apsarasmc.apsaras.event;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.builder.KeyedBuilder;
import org.apsarasmc.apsaras.util.ResourceKey;
import org.apsarasmc.apsaras.util.ResourceKeyed;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;

public interface EventContextKey< T > extends ResourceKeyed {
  @SuppressWarnings ("unchecked")
  static Builder< Object > builder() {
    return Apsaras.injector().getInstance(Builder.class);
  }

  static < T > EventContextKey< T > of(final @Nonnull ResourceKey key, final @Nonnull Class< T > type) {
    return EventContextKey.builder().key(key).type(type).build();
  }

  @Nonnull
  Type allowedType();

  boolean isInstance(Object value);

  T cast(Object value);

  interface Builder< T > extends KeyedBuilder< EventContextKey< T >, Builder< T > > {
    < N > Builder< N > type(Class< N > type);
  }
}
