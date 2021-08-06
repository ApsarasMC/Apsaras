package org.apsarasmc.apsaras.entity;

import net.kyori.adventure.audience.Audience;
import org.apsarasmc.apsaras.Apsaras;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public interface Player extends Entity, Audience {
  static Player.Factory factory(){
    return Apsaras.injector().getInstance(Player.Factory.class);
  }
  boolean isOnline();

  interface Factory{
    Optional< Player > byName(@Nonnull String name);
    Optional< Player > byUUID(@Nonnull UUID uuid);
  }
}
