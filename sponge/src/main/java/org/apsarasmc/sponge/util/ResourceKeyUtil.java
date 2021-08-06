package org.apsarasmc.sponge.util;

import org.apsarasmc.apsaras.util.ResourceKey;

public class ResourceKeyUtil {
  private ResourceKeyUtil(){
    //
  }
  public static ResourceKey to(org.spongepowered.api.ResourceKey resourceKey){
    return ResourceKey.of(resourceKey.namespace(), resourceKey.value());
  }
  public static org.spongepowered.api.ResourceKey to(ResourceKey resourceKey){
    return org.spongepowered.api.ResourceKey.of(resourceKey.namespace(), resourceKey.value());
  }
}
