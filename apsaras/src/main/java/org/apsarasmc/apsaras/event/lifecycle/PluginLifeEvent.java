package org.apsarasmc.apsaras.event.lifecycle;

import org.apsarasmc.apsaras.plugin.PluginContainer;

public interface PluginLifeEvent extends LifeCycleEvent {
  PluginContainer plugin();

  interface Load extends PluginLifeEvent {

  }

  interface Enable extends PluginLifeEvent {

  }

  interface Disable extends PluginLifeEvent {

  }
}
