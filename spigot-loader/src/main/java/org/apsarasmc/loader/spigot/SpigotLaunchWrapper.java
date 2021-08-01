package org.apsarasmc.loader.spigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URL;
import java.util.logging.Level;

public class SpigotLaunchWrapper extends JavaPlugin {
  @Override
  public void onEnable() {
    try {
      String s = SpigotLaunchWrapper.class.getProtectionDomain().getCodeSource().getLocation().toString();
      // No, no! I don't want to close UrlClassPath
      SpigotLoader spigotLoader = new SpigotLoader(
        new URL("jar:" + s + "!/"),
        SpigotLaunchWrapper.class.getClassLoader());
      Class< ? > clazz = spigotLoader.loadClass("org.apsarasmc.spigot.SpigotCore");
      clazz.getMethod("init").invoke(
        clazz.getConstructor(JavaPlugin.class).newInstance(this)
      );
    } catch (Exception e) {
      Bukkit.getLogger().log(Level.WARNING, "Failed to load Apsaras.", e);
    }
  }
}
