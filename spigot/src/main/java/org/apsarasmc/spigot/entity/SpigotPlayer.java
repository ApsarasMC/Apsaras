package org.apsarasmc.spigot.entity;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.apsaras.util.ResourceKey;
import org.apsarasmc.plugin.entity.ImplPlayer;
import org.apsarasmc.spigot.util.KeyUtil;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

public class SpigotPlayer implements ImplPlayer {
  @Nonnull
  private final org.bukkit.entity.Player handle;

  @Nonnull
  private final Audience adventure;

  public SpigotPlayer(final @Nonnull org.bukkit.entity.Player handle) {
    this.handle = handle;
    adventure = Apsaras.injector().getInstance(BukkitAudiences.class).player(this.handle);
  }

  @Override
  public UUID uuid() {
    return handle.getUniqueId();
  }

  @Nonnull
  @Override
  public String name() {
    return handle.getName();
  }

  @Override
  public ResourceKey key() {
    return KeyUtil.to(handle.getType().getKey());
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
  public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
    adventure.sendMessage(source, message, type);
  }

  @Override
  public void playSound(@NotNull Sound sound) {
    adventure.playSound(sound);
  }

  @Override
  public void playSound(@NotNull Sound sound, double x, double y, double z) {
    adventure.playSound(sound, x, y, z);
  }

  @Override
  public void stopSound(@NotNull Sound sound) {
    adventure.stopSound(sound);
  }

  @Override
  public void playSound(@NotNull Sound sound, Sound.@NotNull Emitter emitter) {
    adventure.playSound(sound, emitter);
  }

  @Override
  public void stopSound(@NotNull SoundStop stop) {
    adventure.stopSound(stop);
  }

  @Override
  public void sendActionBar(@NotNull Component message) {
    adventure.sendActionBar(message);
  }

  @Override
  public void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {
    adventure.sendPlayerListHeaderAndFooter(header, footer);
  }

  @Override
  public void showTitle(@NotNull Title title) {
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
  public void openBook(@NotNull Book book) {
    adventure.openBook(book);
  }

  @Singleton
  public static class Factory implements Player.Factory {
    @Override
    public Optional< Player > byName(final @Nonnull String name) {
      return nullableCast(Bukkit.getPlayer(name));
    }

    @Override
    public Optional< Player > byUUID(final @Nonnull UUID uuid) {
      return nullableCast(Bukkit.getPlayer(uuid));
    }

    private Optional< Player > nullableCast(final @Nullable org.bukkit.entity.Player player) {
      if (player == null) {
        return Optional.empty();
      }
      return Optional.of(new SpigotPlayer(player));
    }
  }
}