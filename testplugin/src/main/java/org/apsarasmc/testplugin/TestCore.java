package org.apsarasmc.testplugin;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.event.EventHandler;
import org.apsarasmc.apsaras.event.Order;
import org.apsarasmc.apsaras.event.lifecycle.ServerLoadEvent;
import org.apsarasmc.apsaras.plugin.PluginContainer;

import javax.inject.Inject;

public class TestCore {
  private PluginContainer container;

  @Inject
  public TestCore(PluginContainer container) {
    this.container = container;
  }

  @EventHandler
  public void o(ServerLoadEvent event) {
    Apsaras.server().logger().warn("log by apsaras");
    Apsaras.server().sync().run(container, () -> container.logger().warn("sync"));
    Apsaras.server().uts().run(container, () -> container.logger().warn("uts"));
  }

  @EventHandler (order = Order.PRE)
  public void o1(ServerLoadEvent event) {
    container.logger().info("PRE");
  }

  @EventHandler (order = Order.AFTER_PRE)
  public void o2(ServerLoadEvent event) {
    container.logger().info("AFTER_PRE");
  }

  @EventHandler (order = Order.FIRST)
  public void o3(ServerLoadEvent event) {
    container.logger().info("FIRST");
  }

  @EventHandler (order = Order.EARLY)
  public void o4(ServerLoadEvent event) {
    container.logger().info("EARLY");
  }

  @EventHandler (order = Order.DEFAULT)
  public void o5(ServerLoadEvent event) {
    container.logger().info("DEFAULT");
  }

  @EventHandler (order = Order.LATE)
  public void o6(ServerLoadEvent event) {
    container.logger().info("LATE");
  }

  @EventHandler (order = Order.LAST)
  public void o7(ServerLoadEvent event) {
    container.logger().info("LAST");
  }

  @EventHandler (order = Order.BEFORE_POST)
  public void o8(ServerLoadEvent event) {
    container.logger().info("BEFORE_POST");
  }

  @EventHandler (order = Order.POST)
  public void o9(ServerLoadEvent event) {
    container.logger().info("POST");
  }
}
