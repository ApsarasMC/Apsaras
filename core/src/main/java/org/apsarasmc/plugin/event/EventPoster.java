package org.apsarasmc.plugin.event;

import org.apsarasmc.apsaras.event.Event;
import org.apsarasmc.apsaras.event.Modifiable;
import org.apsarasmc.apsaras.event.Order;

import java.util.Collection;

public class EventPoster<T extends Event> {
    private final T event;
    private final Collection<ImplListenerStruct<T>> listeners;

    public EventPoster(T event, Collection<ImplListenerStruct<T>> listeners) {
        this.event = event;
        this.listeners = listeners;
    }

    public void post() {
        for (Order order : Order.values()) {
            post(order);
        }
    }

    public void post(Order order) {
        if (event == null) {
            return;
        }
        for (ImplListenerStruct<T> listener : listeners) {
            if (listener.order() == order) {
                if (event instanceof Modifiable
                        && ((Modifiable) event).isModified()
                        && listener.beforeModifications()) {
                    continue;
                }
                try {
                    listener.listener().handle(event);
                } catch (Exception exception) {
                    listener.plugin().logger().warn(
                            String.format("Can't pass %s to %s.", event.getClass().getSimpleName(), listener.plugin().name()),
                            exception);
                }
            }
        }
    }
}
