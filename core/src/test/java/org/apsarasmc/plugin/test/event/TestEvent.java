package org.apsarasmc.plugin.test.event;

import org.apsarasmc.apsaras.event.Event;
import org.apsarasmc.apsaras.event.EventContext;

public class TestEvent implements Event {
    private final EventContext context = EventContext.builder().build();

    @Override
    public EventContext context() {
        return this.context;
    }
}
