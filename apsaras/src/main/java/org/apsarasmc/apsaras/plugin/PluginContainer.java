package org.apsarasmc.apsaras.plugin;

import org.apsarasmc.apsaras.util.Named;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.nio.file.Path;

public interface PluginContainer extends Named {

  PluginMeta meta();

  Logger logger();

  void addDepend(PluginContainer depend);

  boolean enabled();

  void load() throws Exception;

  void enable() throws Exception;

  void disable() throws Exception;

  Path dataPath();

  Path configPath();
}
