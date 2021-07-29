package org.apsarasmc.sponge.util;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.plugin.event.EventPoster;
import org.apsarasmc.plugin.event.ImplEventManager;
import org.apsarasmc.sponge.SpongeCore;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventListener;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Vector;

public class EventUtil {
    @Inject
    private static SpongeCore core;
    @Inject
    private static ImplEventManager eventManager;

    private EventUtil() {
        //
    }

    public static <T extends org.spongepowered.api.event.Event> void listen(
            Class<T> eventType, Transfer<T> transfer) {
        Listener<T> listener = new Listener<>(transfer);
        for (org.spongepowered.api.event.Order value : org.spongepowered.api.event.Order.values()) {
            final org.apsarasmc.apsaras.event.Order order = PriorityUtil.of(value);
            Sponge.eventManager().registerListener(core.wrapper(), eventType, value, new EventExecute<>(listener, order));
        }
    }

    public interface Transfer<T extends org.spongepowered.api.event.Event> {
        org.apsarasmc.apsaras.event.Event transfer(@Nonnull T event);
    }

    public static class EventExecute<T extends org.spongepowered.api.event.Event> implements EventListener<T> {
        private final Listener<T> listener;
        private final org.apsarasmc.apsaras.event.Order order;

        public EventExecute(final Listener<T> listener, final org.apsarasmc.apsaras.event.Order order) {
            this.listener = listener;
            this.order = order;
        }

        @Override
        public void handle(final T event) throws Exception {
            listener.execute(event, this.order);
        }
    }

    public static class Listener<T extends org.spongepowered.api.event.Event> {
        private final Transfer<T> transfer;
        private final Collection<InstanceEvent> instanceEvents = new Vector<>();

        public Listener(Transfer<T> transfer) {
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
                    }
                    break;
                }
            }
            if (instanceEvent == null) {
                instanceEvent = new InstanceEvent(event, transfer.transfer(event));
                instanceEvents.add(instanceEvent);
                Apsaras.server().logger().warn("Event " + event + " no PRE priority called.");
            }
            instanceEvent.apsaras.post(order);
            if (order == org.apsarasmc.apsaras.event.Order.POST) {
                InstanceEvent finalInstanceEvent = instanceEvent;
                instanceEvents.removeIf(perInstanceEvent -> perInstanceEvent == finalInstanceEvent);
            }
        }
    }

    public static class InstanceEvent {
        public org.spongepowered.api.event.Event sponge;
        public EventPoster<?> apsaras;

        public InstanceEvent(org.spongepowered.api.event.Event sponge, org.apsarasmc.apsaras.event.Event apsaras) {
            this.sponge = sponge;
            this.apsaras = EventUtil.eventManager.poster(apsaras);
        }
    }
}
