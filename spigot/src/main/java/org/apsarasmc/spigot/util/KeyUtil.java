package org.apsarasmc.spigot.util;

import org.apsarasmc.apsaras.util.ResourceKey;
import org.bukkit.NamespacedKey;

public class KeyUtil {
  private KeyUtil() {
    //
  }

  public static NamespacedKey to(ResourceKey key) {
    return NamespacedKey.fromString(key.toString());
  }

  public static ResourceKey to(NamespacedKey key) {
    return ResourceKey.factory().of(key.getNamespace(), key.getKey());
  }
}
