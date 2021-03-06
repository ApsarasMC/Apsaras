package org.apsarasmc.apsaras.event;

import org.apsarasmc.apsaras.builder.AbstractBuilder;
import org.apsarasmc.apsaras.builder.CopyableBuilder;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public final class EventContext {
  private static final EventContext EMPTY_CONTEXT = new EventContext(new HashMap<>());
  private final Map< EventContextKey< Object >, Object > entries;

  private EventContext(Map< EventContextKey< Object >, Object > values) {
    this.entries = new HashMap<>(values);
  }

  public static EventContext empty() {
    return EventContext.EMPTY_CONTEXT;
  }

  public static EventContext of(Map< EventContextKey< Object >, Object > entries) {
    Objects.requireNonNull(entries, "Context entries cannot be null");
    for (Map.Entry< EventContextKey< Object >, Object > entry : entries.entrySet()) {
      Objects.requireNonNull(entry.getValue(), "Entries cannot contain null values");
    }
    return new EventContext(entries);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings ("unchecked")
  public < T > Optional< T > get(EventContextKey< T > key) {
    Objects.requireNonNull(key, "EventContextKey cannot be null");
    return Optional.ofNullable((T) this.entries.get(key));
  }

  @SuppressWarnings ("unchecked")
  public < T > Optional< T > get(Supplier< EventContextKey< T > > key) {
    Objects.requireNonNull(key, "EventContextKey cannot be null");
    return Optional.ofNullable((T) this.entries.get(key.get()));
  }

  public < T > T require(EventContextKey< T > key) {
    final Optional< T > optional = this.get(key);
    if (optional.isPresent()) {
      return optional.get();
    }
    throw new NoSuchElementException(String.format("Could not retrieve value for key '%s'", key.toString()));
  }

  public < T > T require(Supplier< EventContextKey< T > > key) {
    final Optional< T > optional = this.get(key);
    if (optional.isPresent()) {
      return optional.get();
    }
    throw new NoSuchElementException(String.format("Could not retrieve value for key '%s'", key.get().toString()));
  }

  public boolean containsKey(EventContextKey< ? > key) {
    return this.entries.containsKey(key);
  }

  public boolean containsKey(Supplier< ? extends EventContextKey< ? > > key) {
    return this.entries.containsKey(key.get());
  }

  public Set< EventContextKey< Object > > keySet() {
    return this.entries.keySet();
  }

  public Map< EventContextKey< Object >, Object > asMap() {
    return this.entries;
  }

  @Override
  public boolean equals(@Nullable Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof EventContext)) {
      return false;
    }
    final EventContext ctx = (EventContext) object;
    for (Map.Entry< EventContextKey< Object >, Object > entry : this.entries.entrySet()) {
      final Object other = ctx.entries.get(entry.getKey());
      if (other == null) {
        return false;
      }
      if (!entry.getValue().equals(other)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return this.entries.hashCode();
  }

  public static final class Builder implements AbstractBuilder< EventContext >, CopyableBuilder< EventContext, Builder > {
    private final Map< EventContextKey< Object >, Object > entries = new HashMap<>();

    @SuppressWarnings ("unchecked")
    public < T > Builder add(EventContextKey< T > key, T value) {
      Objects.requireNonNull(value, "Context object cannot be null!");
      if (this.entries.containsKey(key)) {
        throw new IllegalArgumentException("Duplicate context keys: " + key.toString());
      }
      this.entries.put((EventContextKey< Object >) key, value);
      return this;
    }

    @SuppressWarnings ("unchecked")
    public < T > Builder add(Supplier< EventContextKey< T > > key, T value) {
      Objects.requireNonNull(value, "Context object cannot be null!");
      final EventContextKey< T > suppliedKey = key.get();
      Objects.requireNonNull(suppliedKey, "Supplied key cannot be null!");
      if (this.entries.containsKey(suppliedKey)) {
        throw new IllegalArgumentException("Duplicate context keys!");
      }
      this.entries.put((EventContextKey< Object >) suppliedKey, value);
      return this;
    }

    @Override
    public Builder from(EventContext value) {
      this.entries.putAll(value.entries);
      return this;
    }

    @Override
    public EventContext build() {
      return new EventContext(this.entries);
    }
  }
}
