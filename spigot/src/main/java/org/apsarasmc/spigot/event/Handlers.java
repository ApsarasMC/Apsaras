package org.apsarasmc.spigot.event;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Handlers implements EventHandler {
    @Inject
    private LifeCycleHandler lifeCycle;

    @Override
    public void register() {
        lifeCycle.register();
    }
}
