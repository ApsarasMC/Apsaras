package org.apsarasmc.plugin.event;

import org.apsarasmc.apsaras.event.Event;
import org.apsarasmc.apsaras.event.EventListener;
import org.apsarasmc.apsaras.event.Order;
import org.apsarasmc.apsaras.plugin.PluginContainer;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ImplListenerStruct< T extends Event > implements Comparable< ImplListenerStruct< T > > {
  private final @Nonnull
  PluginContainer plugin;
  private final @Nonnull
  Class< T > eventClass;
  private final @Nonnull
  Order order;
  private final boolean beforeModifications;
  private final @Nonnull
  EventListener< T > listener;
  private final @Nonnull
  Set< Class< ? > > listenerEvents = new HashSet<>();

  public ImplListenerStruct(final @Nonnull PluginContainer plugin, final @Nonnull Class< T > eventClass,
                            final @Nonnull Order order, final boolean beforeModifications,
                            final @Nonnull EventListener< T > listener) {
    Objects.requireNonNull(plugin, "plugin");
    Objects.requireNonNull(eventClass, "eventClass");
    Objects.requireNonNull(order, "order");
    Objects.requireNonNull(listener, "listener");
    this.plugin = plugin;
    this.eventClass = eventClass;
    this.order = order;
    this.beforeModifications = beforeModifications;
    this.listener = listener;
  }

  @Nonnull
  public PluginContainer plugin() {
    return plugin;
  }

  @Nonnull
  public Class< T > eventClass() {
    return eventClass;
  }

  @Nonnull
  public Order order() {
    return order;
  }

  public boolean beforeModifications() {
    return beforeModifications;
  }

  @Nonnull
  public EventListener< T > listener() {
    return listener;
  }

  @Nonnull
  public Set< Class< ? > > listenerEvents() {
    return listenerEvents;
  }

  @Override
  public int compareTo(@Nonnull ImplListenerStruct< T > another) {
    int r = this.order.compareTo(another.order);
    return r == 0 ? -1 : r;
  }

  @Override
  public boolean equals(Object o) {
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(plugin, eventClass, order, beforeModifications, listener, listenerEvents);
  }
}
