package org.apsarasmc.spigot.util;

import org.apsarasmc.apsaras.event.Order;
import org.bukkit.event.EventPriority;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;

public enum PriorityUtil {
    LOWEST(EventPriority.LOWEST, new Order[]{
            Order.PRE, Order.AFTER_PRE, Order.FIRST
    }),
    LOW(EventPriority.LOW, new Order[]{
            Order.EARLY
    }),
    NORMAL(EventPriority.NORMAL, new Order[]{
            Order.DEFAULT
    }),
    HIGH(EventPriority.HIGH, new Order[]{
            Order.LATE
    }),
    HIGHEST(EventPriority.HIGHEST, new Order[]{
            Order.LAST, Order.BEFORE_POST
    }),
    MONITOR(EventPriority.MONITOR, new Order[]{
            Order.POST
    });

    private static final Map<EventPriority, PriorityUtil> byPriority = new EnumMap<>(EventPriority.class);
    private static final Map<Order, PriorityUtil> byOrder = new EnumMap<>(Order.class);

    static {
        for (PriorityUtil value : PriorityUtil.values()) {
            byPriority.put(value.priority, value);
            for (Order order : value.orders) {
                byOrder.put(order, value);
            }
        }
    }

    private final EventPriority priority;
    private final Order[] orders;

    PriorityUtil(EventPriority priority, Order[] orders) {
        this.priority = priority;
        this.orders = orders;
    }

    @Nonnull
    public static PriorityUtil of(EventPriority priority) {
        return byPriority.get(priority);
    }

    @Nonnull
    public static PriorityUtil of(Order order) {
        return byOrder.get(order);
    }

    public EventPriority priority() {
        return priority;
    }

    public Order[] orders() {
        return orders;
    }
}
