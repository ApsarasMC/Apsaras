package org.apsarasmc.sponge.event;

import javax.inject.Inject;

public class Handlers implements EventHandler {
    @Inject
    private LifeCycleHandler lifeCycleHandler;

    @Override
    public void register() {
        lifeCycleHandler.register();
    }
}
