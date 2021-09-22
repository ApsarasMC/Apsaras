package org.apsarasmc.sponge.legacy.entity;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.platform.spongeapi.SpongeAudiences;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.apsaras.util.ResourceKey;
import org.apsarasmc.plugin.entity.ImplPlayer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

public class SpongePlayer implements ImplPlayer {
  public final org.spongepowered.api.entity.living.player.Player handle;
  private final Audience adventure;

  public SpongePlayer(org.spongepowered.api.entity.living.player.Player player) {
    this.handle = player;
    this.adventure = Apsaras.injector().getInstance(SpongeAudiences.class).player(handle);
  }

  public org.spongepowered.api.entity.living.player.Player handle() {
    return handle;
  }

  @Override
  public boolean isOnline() {
    return handle.isOnline();
  }

  @Override
  public void showBossBar(final @Nonnull BossBar bar) {
    adventure.showBossBar(bar);
  }

  @Override
  public void hideBossBar(final @Nonnull BossBar bar) {
    adventure.hideBossBar(bar);
  }

  @Override
  public void sendMessage(@Nonnull Identity source, @Nonnull Component message, @Nonnull MessageType type) {
    adventure.sendMessage(source, message, type);
  }

  @Override
  public void playSound(@Nonnull Sound sound) {
    adventure.playSound(sound);
  }

  @Override
  public void playSound(@Nonnull Sound sound, double x, double y, double z) {
    adventure.playSound(sound, x, y, z);
  }

  @Override
  public void stopSound(@Nonnull Sound sound) {
    adventure.stopSound(sound);
  }

  @Override
  public void playSound(@Nonnull Sound sound, @Nonnull Sound.Emitter emitter) {
    adventure.playSound(sound, emitter);
  }

  @Override
  public void stopSound(@Nonnull SoundStop stop) {
    adventure.stopSound(stop);
  }

  @Override
  public void sendActionBar(@Nonnull Component message) {
    adventure.sendActionBar(message);
  }

  @Override
  public void sendPlayerListHeaderAndFooter(@Nonnull Component header, @Nonnull Component footer) {
    adventure.sendPlayerListHeaderAndFooter(header, footer);
  }

  @Override
  public void showTitle(@Nonnull Title title) {
    adventure.showTitle(title);
  }

  @Override
  public void clearTitle() {
    adventure.clearTitle();
  }

  @Override
  public void resetTitle() {
    adventure.resetTitle();
  }

  @Override
  public void openBook(@Nonnull Book book) {
    adventure.openBook(book);
  }

  @Nonnull
  @Override
  public String name() {
    return handle.getName();
  }

  @Override
  @Nonnull
  public ResourceKey key() {
    return ResourceKey.factory().resolve(handle.getType().getId());
  }

  @Override
  public @NotNull Identity identity() {
    return Identity.identity(handle.getUniqueId());
  }

  @Override
  public boolean hasPermission(String name) {
    return handle.hasPermission(name);
  }

  @Singleton
  public static class Factory implements Player.Factory {

    @Override
    public Optional< Player > byName(@Nonnull String name) {
      return nullableCast(Sponge.getServer().getPlayer(name));
    }

    @Override
    public Optional< Player > byUUID(@Nonnull UUID uuid) {
      return nullableCast(Sponge.getServer().getPlayer(uuid));
    }

    public Optional< Player > nullableCast(Optional< org.spongepowered.api.entity.living.player.Player > player) {
      return player.map(SpongePlayer::new);
    }
  }
}
