package org.apsarasmc.apsaras.builder;

import org.apsarasmc.apsaras.util.ResourceKey;
import org.apsarasmc.apsaras.util.ResourceKeyed;

public interface KeyedBuilder< T extends ResourceKeyed, B > extends AbstractBuilder< T > {
  B key(ResourceKey key);
}
