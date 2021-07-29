package org.apsarasmc.apsaras.event;

import org.apsarasmc.apsaras.plugin.PluginContainer;

public interface EventManager {
    void registerListeners(PluginContainer plugin, Object obj);

    <T extends Event> void registerListener(PluginContainer plugin, Class<T> eventClass, EventListener<? super T> listener);

    <T extends Event> void registerListener(PluginContainer plugin, Class<T> eventClass, Order order, EventListener<? super T> listener);

    <T extends Event> void registerListener(PluginContainer plugin, Class<T> eventClass, Order order, boolean beforeModifications,
                                            EventListener<? super T> listener);

    void unregisterListeners(Object obj);

    void unregisterPluginListeners(PluginContainer plugin);

    boolean post(Event event);
}
