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
    List< org.apsarasmc.apsaras.event.Order > orderList = Arrays.asList(org.apsarasmc.apsaras.event.Order.values());
    Collections.shuffle(orderList);
    for (org.apsarasmc.apsaras.event.Order order : orderList) {
      eventManager.registerListener(pluginContainer, TestEvent.class, order, new TestEventListener(order));
    }

    eventManager.post(new TestEvent());

    Assertions.assertEquals(org.apsarasmc.apsaras.event.Order.POST.order() + 1, pos, "Listener called in wrong order.");
  }

  public class TestEventListener implements EventListener< TestEvent > {
    public final org.apsarasmc.apsaras.event.Order order;

    public TestEventListener(final org.apsarasmc.apsaras.event.Order order) {
      this.order = order;
    }

    @Override
    public void handle(final TestEvent event) {
      Assertions.assertEquals(order.order(), pos++);
    }
  }
}
