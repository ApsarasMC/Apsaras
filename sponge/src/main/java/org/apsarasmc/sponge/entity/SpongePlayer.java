package org.apsarasmc.sponge.entity;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.apsaras.util.ResourceKey;
import org.apsarasmc.plugin.entity.ImplPlayer;
import org.apsarasmc.sponge.util.ResourceKeyUtil;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.registry.RegistryTypes;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

public class SpongePlayer implements ImplPlayer {
  public final ServerPlayer handle;

  public SpongePlayer(ServerPlayer player) {
    this.handle = player;
  }

  public ServerPlayer handle() {
    return handle;
  }

  @Override
  public boolean isOnline() {
    return handle.isOnline();
  }

  @Override
  public void showBossBar(final @Nonnull BossBar bar) {
    handle.showBossBar(bar);
  }

  @Override
  public void hideBossBar(final @Nonnull BossBar bar) {
    handle.hideBossBar(bar);
  }

  @Override
  public void sendMessage(@Nonnull Identity source, @Nonnull Component message, @Nonnull MessageType type) {
    handle.sendMessage(source, message, type);
  }

  @Override
  public void playSound(@Nonnull Sound sound) {
    handle.playSound(sound);
  }

  @Override
  public void playSound(@Nonnull Sound sound, double x, double y, double z) {
    handle.playSound(sound, x, y, z);
  }

  @Override
  public void stopSound(@Nonnull Sound sound) {
    handle.stopSound(sound);
  }

  @Override
  public void playSound(@Nonnull Sound sound, @Nonnull Sound.Emitter emitter) {
    handle.playSound(sound, emitter);
  }

  @Override
  public void stopSound(@Nonnull SoundStop stop) {
    handle.stopSound(stop);
  }

  @Override
  public void sendActionBar(@Nonnull Component message) {
    handle.sendActionBar(message);
  }

  @Override
  public void sendPlayerListHeaderAndFooter(@Nonnull Component header, @Nonnull Component footer) {
    handle.sendPlayerListHeaderAndFooter(header, footer);
  }

  @Override
  public void showTitle(@Nonnull Title title) {
    handle.showTitle(title);
  }

  @Override
  public void clearTitle() {
    handle.clearTitle();
  }

  @Override
  public void resetTitle() {
    handle.resetTitle();
  }

  @Override
  public void openBook(@Nonnull Book book) {
    handle.openBook(book);
  }

  @Nonnull
  @Override
  public String name() {
    return handle.name();
  }

  @Override
  @Nonnull
  public ResourceKey key() {
    return ResourceKeyUtil.to(handle.type().key(RegistryTypes.ENTITY_TYPE));
  }

  @Override
  public @NotNull Identity identity() {
    return handle.identity();
  }

  @Override
  public boolean hasPermission(String name) {
    return handle.hasPermission(name);
  }

  @Singleton
  public static class Factory implements Player.Factory {

    @Override
    public Optional< Player > byName(@Nonnull String name) {
      return nullableCast(Sponge.server().player(name));
    }

    @Override
    public Optional< Player > byUUID(@Nonnull java.util.UUID uuid) {
      return nullableCast(Sponge.server().player(uuid));
    }

    public Optional< Player > nullableCast(Optional< ServerPlayer > player) {
      return player.map(SpongePlayer::new);
    }
  }
}
