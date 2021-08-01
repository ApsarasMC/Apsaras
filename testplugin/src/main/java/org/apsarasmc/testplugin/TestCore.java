package org.apsarasmc.testplugin;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.aop.Component;
import org.apsarasmc.apsaras.event.EventHandler;
import org.apsarasmc.apsaras.event.Order;
import org.apsarasmc.apsaras.event.lifecycle.ServerLoadEvent;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.testplugin.aaa.TTT;

@Component
public class TestCore {
  public static final String TEST_CLASS = "org.apsarasmc.testplugin.aaa.TTT";
  private PluginContainer container;

  public TestCore() {
    container = Apsaras.injector().getInstance(PluginContainer.class);
    ClassLoader classLoader = TestCore.class.getClassLoader();
    container.logger().info("Thread.getContextClassLoader() : {}", Thread.currentThread().getContextClassLoader());
    container.logger().info("class.getClassLoader() : {}", Thread.currentThread().getContextClassLoader());
    String testName;

    testName = "Direct use";
    try {
      container.logger().info("Success: {} : {}", testName,
        TTT.class.getName());
    } catch (Exception e) {
      container.logger().warn("Failure: {} : {}", testName, e);
    }

    testName = "Class.forName";
    try {
      container.logger().info("Success: {} : {}", testName,
        Class.forName(TEST_CLASS).getName());
    } catch (Exception e) {
      container.logger().warn("Failure: {} : {}", testName, e);
    }

    testName = "Reflection Class.forName(String)";
    try {
      container.logger().info("Success: {} : {}", testName,
        ((Class<?>)Class.class.getMethod("forName", String.class)
          .invoke(null, TEST_CLASS)).getName());
    } catch (Exception e) {
      container.logger().warn("Failure: {} : {}", testName, e);
    }

    testName = "Class.forName(String,boolean,ClassLoader)";
    try {
      container.logger().info("Success: {} : {}", testName,
        Class.forName(TEST_CLASS, false, classLoader).getName());
    } catch (Exception e) {
      container.logger().warn("Failure: {} : {}", testName, e);
    }

    testName = "Reflection Class.forName(String,boolean,ClassLoader)";
    try {
      container.logger().info("Success: {} : {}", testName,
        ((Class<?>)Class.class.getMethod("forName", String.class, boolean.class, ClassLoader.class)
          .invoke(null, TEST_CLASS, false, classLoader)).getName());
    } catch (Exception e) {
      container.logger().warn("Failure: {} : {}", testName, e);
    }

    testName = "ClassLoader.loadClass(String)";
    try {
      container.logger().info("Success: {} : {}", testName,
        classLoader.loadClass(TEST_CLASS).getName());
    } catch (Exception e) {
      container.logger().warn("Failure: {} : {}", testName, e);
    }

    testName = "Reflection ClassLoader.loadClass(String)";
    try {
      container.logger().info("Success: {} : {}", testName,
        ((Class<?>)classLoader.getClass().getMethod("loadClass", String.class)
          .invoke(classLoader, TEST_CLASS)).getName());
    } catch (Exception e) {
      container.logger().warn("Failure: {} : {}", testName, e);
    }

    testName = "Class.getName()";
    try {
      container.logger().info("Success: {} : {}", testName,
        TTT.class.getName());
    } catch (Exception e) {
      container.logger().warn("Failure: {} : {}", testName, e);
    }

    testName = "Reflection Class.getName()";
    try {
      container.logger().info("Success: {} : {}", testName,
        Class.class.getMethod("getName").invoke(TTT.class));
    } catch (Exception e) {
      container.logger().warn("Failure: {} : {}", testName, e);
    }
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
