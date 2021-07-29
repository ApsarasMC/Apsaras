package org.apsarasmc.apsaras.plugin;

import java.io.File;
import java.util.Collection;

public interface PluginManager {
    Collection<PluginContainer> getPlugins();

    void addPlugin(File pluginFile);

    void addPlugin(PluginContainer plugin);

    void load();

    void enable();
}
