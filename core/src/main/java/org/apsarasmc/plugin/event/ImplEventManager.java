package org.apsarasmc.plugin.event;

import org.apsarasmc.apsaras.event.EventListener;
import org.apsarasmc.apsaras.event.*;
import org.apsarasmc.apsaras.plugin.PluginContainer;

import javax.inject.Singleton;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class ImplEventManager implements EventManager {
  private static final Class< Event > EVENT_CLASS = Event.class;
  private final Map< Class< ? extends Event >, Collection< ImplListenerStruct< ? extends Event > > > linkedHandlers = new ConcurrentHashMap<>();
  private final Collection< Class< ? extends Event > > events = Collections.synchronizedSet(new HashSet<>());
  private final Collection< ImplListenerStruct< ? extends Event > > listeners = Collections.synchronizedSet(new HashSet<>());

  @Override
  @SuppressWarnings ({ "unchecked" })
  public void registerListeners(PluginContainer plugin, Object obj) {
    Arrays.stream(obj.getClass().getMethods())
      .filter(method -> method.getReturnType().equals(void.class))
      .filter(method -> Modifier.isPublic(Modifier.methodModifiers()))
      .filter(method -> method.getAnnotation(EventHandler.class) != null)
      .filter(method -> method.getParameters().length == 1)
      .filter(method -> EVENT_CLASS.isAssignableFrom(method.getParameters()[ 0 ].getType()))
      .forEach(method -> {
        EventHandler eventAnnotation = method.getAnnotation(EventHandler.class);
        this.registerListener(plugin, (Class< ? extends Event >) method.getParameters()[ 0 ].getType(),
          eventAnnotation.order(), eventAnnotation.beforeModifications(),
          new ImplEventListener<>(plugin, obj, method));
      });
  }

  @Override
  public < T extends Event > void registerListener(PluginContainer plugin, Class< T > eventClass, EventListener< T > listener) {
    this.registerListener(plugin, eventClass, Order.DEFAULT, listener);
  }

  @Override
  public < T extends Event > void registerListener(PluginContainer plugin, Class< T > eventClass, Order order, EventListener< T > listener) {
    this.registerListener(plugin, eventClass, order, false, listener);
  }

  @Override
  public < T extends Event > void registerListener(PluginContainer plugin, Class< T > eventClass, Order order, boolean beforeModifications, EventListener< T > listener) {
    ImplListenerStruct< T > listenerStruct = new ImplListenerStruct<>(plugin, eventClass, order, beforeModifications, listener);
    listeners.add(listenerStruct);
    events.stream()
      .filter(v -> listenerStruct.eventClass().isAssignableFrom(v))
      .forEach(v -> {
        listenerStruct.listenerEvents().add(v);
        linkedHandlers.get(v).add(listenerStruct);
      });
  }

  @Override
  @SuppressWarnings ("deprecation")
  public void unregisterListeners(Object obj) {
    Arrays.stream(obj.getClass().getMethods())
      .filter(method -> method.getReturnType().equals(void.class))
      .filter(AccessibleObject::isAccessible)
      .filter(method -> method.getAnnotation(EventHandler.class) != null)
      .filter(method -> method.getParameters().length == 1)
      .filter(method -> EVENT_CLASS.isAssignableFrom(method.getParameters()[ 0 ].getType()))
      .forEach(method -> listeners.stream()
        .filter(v -> v.listener() instanceof ImplEventListener)
        .filter(v -> ((ImplEventListener< ? >) v.listener()).object() == obj)
        .filter(v -> ((ImplEventListener< ? >) v.listener()).method().equals(method))
        .forEach(v -> {
          listeners.remove(v);
          v.listenerEvents().forEach(iv -> linkedHandlers.get(iv).remove(v));
        }));
  }

  @Override
  public void unregisterPluginListeners(PluginContainer plugin) {
    listeners.stream()
      .filter(v -> v.plugin().equals(plugin))
      .forEach(v -> {
        listeners.remove(v);
        v.listenerEvents().forEach(iv -> linkedHandlers.get(iv).remove(v));
      });
  }

  @SuppressWarnings ("unchecked")
  public void registerEvent(Class< ? > clazz) {
    if (clazz == null || !EVENT_CLASS.isAssignableFrom(clazz)) {
      return;
    }
    Class< ? extends Event > eventClass = (Class< ? extends Event >) clazz;
    if (events.contains(eventClass)) {
      return;
    }

    events.add(eventClass);
    Collection< ImplListenerStruct< ? > > listenerStructs = new TreeSet<>();
    linkedHandlers.put(eventClass, listenerStructs);
    listeners.stream().filter(v -> v.eventClass().isAssignableFrom(eventClass)).forEach(v -> {
      listenerStructs.add(v);
      v.listenerEvents().add(eventClass);
    });


    for (Class< ? > eventInterface : eventClass.getInterfaces()) {
      registerEvent(eventInterface);
    }
    registerEvent(eventClass.getSuperclass());
  }

  @Override
  public boolean post(Event event) {
    poster(event).post();
    if (event instanceof Cancellable) {
      return ((Cancellable) event).cancelled();
    } else {
      return true;
    }
  }

  @SuppressWarnings ({ "unchecked", "rawtypes" })
  public < T extends Event > EventPoster< T > poster(T event) {
    if (!events.contains(event.getClass())) {
      this.registerEvent(event.getClass());
    }
    return new EventPoster(
      event,
      linkedHandlers.get(event.getClass())
        .stream()
        .map(ImplListenerStruct.class::cast)
        .collect(Collectors.toList()));
  }
}
