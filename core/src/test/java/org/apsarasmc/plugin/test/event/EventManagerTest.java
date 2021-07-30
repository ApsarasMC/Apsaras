package org.apsarasmc.plugin.test.event;

import org.apsarasmc.apsaras.event.EventHandler;
import org.apsarasmc.apsaras.event.EventListener;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.plugin.ApsarasPluginContainer;
import org.apsarasmc.plugin.test.InjectTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@InjectTest
class EventManagerTest {
  @Inject
  private ApsarasPluginContainer pluginContainer;
  @Inject
  private EventManager eventManager;

  private int pos = org.apsarasmc.apsaras.event.Order.PRE.order();

  @Test
  @Order (0)
  void register() {
    eventManager.registerListeners(pluginContainer, this);

    List< org.apsarasmc.apsaras.event.Order > orderList = Arrays.asList(org.apsarasmc.apsaras.event.Order.values());
    Collections.shuffle(orderList);
    for (org.apsarasmc.apsaras.event.Order order : orderList) {
      eventManager.registerListener(pluginContainer, TestEvent.class, order, new TestEventListener(order));
    }

    eventManager.post(new TestEvent());

    Assertions.assertEquals(org.apsarasmc.apsaras.event.Order.POST.order() + 1, pos, "Listener called in wrong order.");
  }

  @EventHandler (order = org.apsarasmc.apsaras.event.Order.PRE)
  public void vb4(TestEvent event) {
    if (pos == -4) {
      pos++;
    }
  }

  @EventHandler (order = org.apsarasmc.apsaras.event.Order.AFTER_PRE)
  public void vb3(TestEvent event) {
    if (pos == -3) {
      pos++;
    }
  }

  @EventHandler (order = org.apsarasmc.apsaras.event.Order.FIRST)
  public void vb2(TestEvent event) {
    if (pos == -2) {
      pos++;
    }
  }

  @EventHandler (order = org.apsarasmc.apsaras.event.Order.EARLY)
  public void vb1(TestEvent event) {
    if (pos == -1) {
      pos++;
    }
  }

  @EventHandler (order = org.apsarasmc.apsaras.event.Order.DEFAULT)
  public void v0(TestEvent event) {
    if (pos == 0) {
      pos++;
    }
  }

  @EventHandler (order = org.apsarasmc.apsaras.event.Order.LATE)
  public void va1(TestEvent event) {
    if (pos == 1) {
      pos++;
    }
  }

  @EventHandler (order = org.apsarasmc.apsaras.event.Order.LAST)
  public void va2(TestEvent event) {
    if (pos == 2) {
      pos++;
    }
  }

  @EventHandler (order = org.apsarasmc.apsaras.event.Order.BEFORE_POST)
  public void va3(TestEvent event) {
    if (pos == 3) {
      pos++;
    }
  }

  @EventHandler (order = org.apsarasmc.apsaras.event.Order.POST)
  public void va4(TestEvent event) {
    if (pos == 4) {
      pos++;
    }
  }

  public class TestEventListener implements EventListener< TestEvent > {
    public final org.apsarasmc.apsaras.event.Order order;

    public TestEventListener(final org.apsarasmc.apsaras.event.Order order) {
      this.order = order;
    }

    @Override
    public void handle(final TestEvent event) throws Exception {
      Assertions.assertEquals(order.order(), pos++);
    }
  }
}
