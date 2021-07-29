package org.apsarasmc.plugin.event.lifecycle;

import org.apsarasmc.apsaras.event.EventContext;
import org.apsarasmc.apsaras.event.lifecycle.ServerLoadEvent;

public class ImplServerLoadEvent implements ServerLoadEvent {
    private final EventContext context = EventContext.builder().build();

    @Override
    public EventContext context() {
        return this.context;
    }
}
