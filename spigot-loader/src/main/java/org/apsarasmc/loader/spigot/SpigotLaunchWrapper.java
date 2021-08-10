package org.apsarasmc.loader.spigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URL;
import java.util.logging.Level;

public class SpigotLaunchWrapper extends JavaPlugin {
  public static final String CORE_CLASS_NAME = "org.apsarasmc.spigot.SpigotCore";
  private SpigotLoader spigotLoader;
  private Class<?> coreClass;
  private Object spigotCore;
  @Override
  public void onEnable() {
    try {
      String s = SpigotLaunchWrapper.class.getProtectionDomain().getCodeSource().getLocation().toString();
      // No, no! I don't want to close UrlClassPath
      spigotLoader = new SpigotLoader(
        new URL("jar:" + s + "!/"),
        SpigotLaunchWrapper.class.getClassLoader());
      coreClass = spigotLoader.loadClass(CORE_CLASS_NAME);
      spigotCore = coreClass.getConstructor(JavaPlugin.class).newInstance(this);
      coreClass.getMethod("enable").invoke(spigotCore);
    } catch (Exception e) {
      Bukkit.getLogger().log(Level.WARNING, "Failed to load Apsaras.", e);
    }
  }

  @Override
  public void onDisable() {
    try {
      coreClass.getMethod("disable").invoke(spigotCore);
    } catch (Exception e) {
      Bukkit.getLogger().log(Level.WARNING, "Failed to unload Apsaras.", e);
    }
  }
}
