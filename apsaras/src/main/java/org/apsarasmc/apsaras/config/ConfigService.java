package org.apsarasmc.apsaras.config;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.plugin.PluginContainer;

public interface ConfigService< T > {
  static ConfigService.Factory factory() {
    return Apsaras.injector().getInstance(ConfigService.Factory.class);
  }

  T load() throws Exception;

  void save(T object) throws Exception;

  interface Factory {
    < T > ConfigService< T > of(PluginContainer pluginContainer, String name, Class< T > configClass);
  }
}
