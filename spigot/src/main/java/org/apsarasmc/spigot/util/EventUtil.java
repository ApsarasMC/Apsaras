package org.apsarasmc.spigot.util;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.event.Order;
import org.apsarasmc.plugin.event.EventPoster;
import org.apsarasmc.plugin.event.ImplEventManager;
import org.apsarasmc.spigot.SpigotCore;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Vector;

public class EventUtil {
    @Inject
    private static ImplEventManager eventManager;

    @Inject
    private static SpigotCore core;

    private EventUtil() {
        //
    }

    public static <T extends org.bukkit.event.Event> void listen(
            Class<T> eventType, EventUtil.Transfer<T> transfer) {
        Listener<T> listener = new Listener<>(transfer);
        for (PriorityUtil priority : PriorityUtil.values()) {
            Bukkit.getPluginManager().registerEvent(
                    eventType,
                    listener,
                    priority.priority(),
                    (ignore, event) -> {
                        listener.execute((T) event, priority);
                    }, core.wrapper());
        }
    }

    public interface Transfer<T extends org.bukkit.event.Event> {
        org.apsarasmc.apsaras.event.Event transfer(@Nonnull T event);
    }

    public static class Listener<T extends org.bukkit.event.Event> implements org.bukkit.event.Listener {
        private final Transfer<T> transfer;
        private final Collection<InstanceEvent> instanceEvents = new Vector<>();

        public Listener(Transfer<T> transfer) {
            this.transfer = transfer;
        }

        public void execute(T event, PriorityUtil priority) {
            InstanceEvent instanceEvent = null;
            if (priority == PriorityUtil.LOWEST) {
                instanceEvent = new InstanceEvent(event, transfer.transfer(event));
                instanceEvents.add(instanceEvent);
            } else {
                for (InstanceEvent perInstanceEvent : instanceEvents) {
                    if (perInstanceEvent.bukkit == event) {
                        instanceEvent = perInstanceEvent;
                    }
                    break;
                }
            }
            if (instanceEvent == null) {
                instanceEvent = new InstanceEvent(event, transfer.transfer(event));
                instanceEvents.add(instanceEvent);
                Apsaras.server().logger().warn("Event " + event + " no LOWEST priority called.");
            }
            for (Order order : priority.orders()) {
                instanceEvent.apsaras.post(order);
            }
            if (priority == PriorityUtil.MONITOR) {
                InstanceEvent finalInstanceEvent = instanceEvent;
                instanceEvents.removeIf(perInstanceEvent -> perInstanceEvent == finalInstanceEvent);
            }
        }
    }

    public static class InstanceEvent {
        public org.bukkit.event.Event bukkit;
        public EventPoster<?> apsaras;

        public InstanceEvent(org.bukkit.event.Event bukkit, org.apsarasmc.apsaras.event.Event apsaras) {
            this.bukkit = bukkit;
            this.apsaras = EventUtil.eventManager.poster(apsaras);
        }

    }
}
