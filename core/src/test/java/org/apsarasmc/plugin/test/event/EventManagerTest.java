package org.apsarasmc.plugin.test.event;

import org.apsarasmc.apsaras.event.EventHandler;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.plugin.ApsarasPluginContainer;
import org.apsarasmc.plugin.test.InjectTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@InjectTest
public class EventManagerTest {
    @Inject
    private ApsarasPluginContainer pluginContainer;
    @Inject
    private EventManager eventManager;

    private int pos = -4;

    @Test
    @Order(0)
    public void register() {
        eventManager.registerListeners(pluginContainer, this);
        eventManager.post(new TestEvent());
        Assertions.assertEquals(5, pos, "Listener called in wrong order.");
    }

    @EventHandler(order = org.apsarasmc.apsaras.event.Order.PRE)
    public void vb4(TestEvent event) {
        if (pos == -4) {
            pos++;
        }
    }

    @EventHandler(order = org.apsarasmc.apsaras.event.Order.AFTER_PRE)
    public void vb3(TestEvent event) {
        if (pos == -3) {
            pos++;
        }
    }

    @EventHandler(order = org.apsarasmc.apsaras.event.Order.FIRST)
    public void vb2(TestEvent event) {
        if (pos == -2) {
            pos++;
        }
    }

    @EventHandler(order = org.apsarasmc.apsaras.event.Order.EARLY)
    public void vb1(TestEvent event) {
        if (pos == -1) {
            pos++;
        }
    }

    @EventHandler(order = org.apsarasmc.apsaras.event.Order.DEFAULT)
    public void v0(TestEvent event) {
        if (pos == 0) {
            pos++;
        }
    }

    @EventHandler(order = org.apsarasmc.apsaras.event.Order.LATE)
    public void va1(TestEvent event) {
        if (pos == 1) {
            pos++;
        }
    }

    @EventHandler(order = org.apsarasmc.apsaras.event.Order.LAST)
    public void va2(TestEvent event) {
        if (pos == 2) {
            pos++;
        }
    }

    @EventHandler(order = org.apsarasmc.apsaras.event.Order.BEFORE_POST)
    public void va3(TestEvent event) {
        if (pos == 3) {
            pos++;
        }
    }

    @EventHandler(order = org.apsarasmc.apsaras.event.Order.POST)
    public void va4(TestEvent event) {
        if (pos == 4) {
            pos++;
        }
    }
}
