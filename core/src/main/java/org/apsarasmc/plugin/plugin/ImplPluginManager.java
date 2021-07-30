package org.apsarasmc.plugin.plugin;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.plugin.PluginDepend;
import org.apsarasmc.apsaras.plugin.PluginManager;

import javax.inject.Singleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Singleton
public class ImplPluginManager implements PluginManager {
  public static final String FAILED_TO_LOAD = "Failed to load plugin {}.";
  private final Collection< PluginContainer > plugins = new ArrayList<>();

  @Override
  public Collection< PluginContainer > getPlugins() {
    return new ArrayList<>(plugins);
  }

  @Override
  public void addPlugin(File pluginFile) {
    if (pluginFile.getName().endsWith(".jar")) {
      this.addPlugin(new JavaPluginContainer(pluginFile));
    }
  }

  @Override
  public void addPlugin(PluginContainer plugin) {
    plugins.add(plugin);
  }

  @Override
  public void load() {
    this.load(false);
  }

  public void load(boolean ignoreNotApsaras) {
    loadPlugins(ignoreNotApsaras);
    checkPluginsDepends();
    Apsaras.server().logger().info("Loaded plugin(s): {}.", plugins);
  }

  public void loadPlugins(boolean ignoreNotApsaras) {
    Iterator< PluginContainer > itr = plugins.iterator();
    while (itr.hasNext()) {
      PluginContainer plugin = itr.next();
      try {
        plugin.load();
      } catch (FileNotFoundException e) {
        if (!ignoreNotApsaras) {
          Apsaras.server().logger().warn(FAILED_TO_LOAD, plugin, e);
        }
        itr.remove();
      } catch (Exception e) {
        Apsaras.server().logger().warn(FAILED_TO_LOAD, plugin, e);
        itr.remove();
      }
    }
  }

  public void checkPluginsDepends() {
    Map< String, PluginContainer > pluginContainerMap = new HashMap<>();
    for (PluginContainer plugin : plugins) {
      pluginContainerMap.put(plugin.name(), plugin);
    }
    Iterator< PluginContainer > itr = plugins.iterator();
    while (itr.hasNext()) {
      PluginContainer plugin = itr.next();
      try {
        checkPluginDepends(pluginContainerMap, plugin);
      } catch (Exception e) {
        itr.remove();
        Apsaras.server().logger().warn(FAILED_TO_LOAD, plugin, e);
      }
    }
  }

  public void checkPluginDepends(Map< String, PluginContainer > pluginContainerMap, PluginContainer plugin) {
    for (PluginDepend depend : plugin.meta().depends()) {
      if (depend.type().equals(PluginDepend.Type.REQUIRE) || depend.type().equals(PluginDepend.Type.SOFT)
        || depend.type().equals(PluginDepend.Type.COOPERATE)) {
        PluginContainer target = pluginContainerMap.get(depend.name());
        if (target == null) {
          if (depend.type().equals(PluginDepend.Type.SOFT)) {
            continue;
          } else {
            throw new NullPointerException("Can't found " + plugin.name() + "'s depend " + depend.name());
          }
        }
        plugin.addDepend(target);
      }
    }
  }

  @Override
  public void enable() {
    for (PluginContainer plugin : plugins) {
      try {
        plugin.enable();
      } catch (Exception e) {
        Apsaras.server().logger().warn("Failed to enable plugin " + plugin.name() + ".", e);
      }
    }
  }
}
