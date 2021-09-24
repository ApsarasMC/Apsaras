package org.apsarasmc.apsaras.entity;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.command.CommandSender;
import org.apsarasmc.apsaras.util.ResourceKey;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public interface Player extends Entity, CommandSender , PermissionCheckable{
  static Player.Factory factory() {
    return Apsaras.injector().getInstance(Player.Factory.class);
  }

  boolean isOnline();

  String placeholder(ResourceKey key);

  interface Factory {
    Optional< Player > byName(@Nonnull String name);

    Optional< Player > byUUID(@Nonnull UUID uuid);
  }
}
