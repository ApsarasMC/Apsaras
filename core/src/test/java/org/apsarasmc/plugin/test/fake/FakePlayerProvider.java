package org.apsarasmc.plugin.test.fake;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.apsaras.util.ResourceKey;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class FakePlayerProvider implements Provider< Player > {
  int id = 0;
  @Override
  public Player get() {
    return new Player() {
      Identity identity = Identity.identity(UUID.randomUUID());
      String name = "player_" + id++;
      @Override
      public ResourceKey key() {
        return ResourceKey.factory().of("test","player");
      }

      @NotNull
      @Override
      public String name() {
        return name;
      }

      @Override
      public boolean hasPermission(String name) {
        return true;
      }

      @Override
      public @NotNull Identity identity() {
        return identity;
      }

      @Override
      public boolean isOnline() {
        return true;
      }

      @Override
      public void sendMessage(@Nonnull Identity source, @Nonnull Component message, @Nonnull MessageType type) {
        Apsaras.server().logger().info("{} received message : {}",name,message);
      }
    };
  }
}
