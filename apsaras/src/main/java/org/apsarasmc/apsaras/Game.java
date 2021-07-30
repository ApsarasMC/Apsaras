package org.apsarasmc.apsaras;

import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.plugin.PluginManager;

public interface Game {
  Server server();

  PluginManager pluginManager();

  EventManager eventManager();

  Injector injector();

  PluginContainer self();
}
