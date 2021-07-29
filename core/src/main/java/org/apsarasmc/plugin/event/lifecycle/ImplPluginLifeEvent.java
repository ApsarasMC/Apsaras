package org.apsarasmc.plugin.event.lifecycle;

import org.apsarasmc.apsaras.event.EventContext;
import org.apsarasmc.apsaras.event.lifecycle.PluginLifeEvent;
import org.apsarasmc.apsaras.plugin.PluginContainer;

public class ImplPluginLifeEvent implements PluginLifeEvent {
    private final EventContext context = EventContext.builder().build();
    private final PluginContainer plugin;

    private ImplPluginLifeEvent(PluginContainer plugin) {
        this.plugin = plugin;
    }

    @Override
    public EventContext context() {
        return this.context;
    }

    @Override
    public PluginContainer plugin() {
        return this.plugin;
    }

    public static class Load extends ImplPluginLifeEvent implements PluginLifeEvent.Load {
        public Load(PluginContainer plugin) {
            super(plugin);
        }
    }

    public static class Enable extends ImplPluginLifeEvent implements PluginLifeEvent.Load {
        public Enable(PluginContainer plugin) {
            super(plugin);
        }
    }

    public static class Disable extends ImplPluginLifeEvent implements PluginLifeEvent.Load {
        public Disable(PluginContainer plugin) {
            super(plugin);
        }
    }
}
