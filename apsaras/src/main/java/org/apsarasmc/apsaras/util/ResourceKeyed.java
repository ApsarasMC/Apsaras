package org.apsarasmc.apsaras.util;

import net.kyori.adventure.key.Keyed;

public interface ResourceKeyed extends Keyed {

    @Override
    ResourceKey key();

}