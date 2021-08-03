package org.apsarasmc.plugin.util.relocate;

import org.apsarasmc.plugin.plugin.JavaPluginContainer;
import org.apsarasmc.plugin.plugin.JavaPluginLoader;

public class PluginContainerEntry {
  private static JavaPluginContainer container;

  private PluginContainerEntry() {
    //
  }

  public static JavaPluginContainer container() {
    return container;
  }

  public static JavaPluginLoader pluginLoader() {
    return container().pluginLoader();
  }

  public static RelocatingRemapper remapper() {
    return pluginLoader().remapper();
  }

  public static Class< ? > forName(String className) throws ClassNotFoundException {
    return forName(className, true, pluginLoader());
  }

  public static Class< ? > forName(String className, boolean initialize, ClassLoader loader) throws ClassNotFoundException {
    return Class.forName(remapper().mapClass(className), initialize, loader);
  }

  public static String getName(Class< ? > clazz) {
    return remapper().unMapClass(clazz.getName());
  }

  public static String getSimpleName(Class< ? > clazz) {
    String result = remapper().unMapClass(clazz.getName());
    return result.substring(result.lastIndexOf('.'));
  }

  public static Class< ? > loadClass(ClassLoader loader, String className) throws ClassNotFoundException {
    return loader.loadClass(className);
  }

  public static ClassLoader getContextClassLoader(Thread thread) {
    return pluginLoader();
  }
}
