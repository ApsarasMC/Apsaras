package org.apsarasmc.sponge.util;

import io.leangen.geantyref.TypeToken;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.plugin.event.EventPoster;
import org.apsarasmc.plugin.event.ImplEventManager;
import org.apsarasmc.sponge.SpongeCore;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.EventListenerRegistration;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

public class EventUtil {
  @Inject
  private static SpongeCore core;
  @Inject
  private static ImplEventManager eventManager;

  private EventUtil() {
    //
  }

  public static < T extends org.spongepowered.api.event.Event > void listen(
    Class< T > eventType, Transfer< T > transfer) {
    listen(TypeToken.get(eventType),transfer);
  }

  public static < T extends org.spongepowered.api.event.Event > void listen(
    TypeToken< T > eventType, Transfer< T > transfer) {
    Listener< T > listener = new Listener<>(transfer);
    for (org.spongepowered.api.event.Order value : org.spongepowered.api.event.Order.values()) {
      final org.apsarasmc.apsaras.event.Order order = PriorityUtil.of(value);
      Sponge.eventManager().registerListener(
        EventListenerRegistration.builder(eventType)
          .plugin(core.wrapper())
          .order(value)
          .listener(new EventExecute<>(listener, order))
          .build()
      );
    }
  }

  public interface Transfer< T extends org.spongepowered.api.event.Event > {
    org.apsarasmc.apsaras.event.Event transfer(@Nonnull T event);
  }

  public static class EventExecute< T extends org.spongepowered.api.event.Event > implements EventListener< T > {
    private final Listener< T > listener;
    private final org.apsarasmc.apsaras.event.Order order;

    public EventExecute(final Listener< T > listener, final org.apsarasmc.apsaras.event.Order order) {
      this.listener = listener;
      this.order = order;
    }

    @Override
    public void handle(final T event) {
      listener.execute(event, this.order);
    }
  }

  public static class Listener< T extends org.spongepowered.api.event.Event > {
    private static final Logger logger = Apsaras.server().logger();
    private final Transfer< T > transfer;
    private final Collection< InstanceEvent > instanceEvents = new ArrayList<>();

    public Listener(Transfer< T > transfer) {
      this.transfer = transfer;
    }

    public void execute(T event, org.apsarasmc.apsaras.event.Order order) {
      InstanceEvent instanceEvent = null;
      if (order == org.apsarasmc.apsaras.event.Order.PRE) {
        instanceEvent = new InstanceEvent(event, transfer.transfer(event));
        instanceEvents.add(instanceEvent);
      } else {
        for (InstanceEvent perInstanceEvent : instanceEvents) {
          if (perInstanceEvent.sponge == event) {
            instanceEvent = perInstanceEvent;
            break;
          }
        }
      }
      if (instanceEvent == null) {
        instanceEvent = new InstanceEvent(event, transfer.transfer(event));
        instanceEvents.add(instanceEvent);
        logger.warn("Event {} no PRE priority called.", event);
      }
      instanceEvent.apsaras.post(order);
      if (order == org.apsarasmc.apsaras.event.Order.POST) {
        InstanceEvent finalInstanceEvent = instanceEvent;
        instanceEvents.removeIf(perInstanceEvent -> perInstanceEvent == finalInstanceEvent);
      }
    }
  }

  public static class InstanceEvent {
    private final org.spongepowered.api.event.Event sponge;
    private final EventPoster< ? > apsaras;

    public InstanceEvent(org.spongepowered.api.event.Event sponge, org.apsarasmc.apsaras.event.Event apsaras) {
      this.sponge = sponge;
      this.apsaras = EventUtil.eventManager.poster(apsaras);
    }
  }
}
