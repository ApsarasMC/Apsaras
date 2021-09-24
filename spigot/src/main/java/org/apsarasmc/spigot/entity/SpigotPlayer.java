package org.apsarasmc.spigot.entity;

import me.clip.placeholderapi.PlaceholderAPI;
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
import org.apsarasmc.spigot.SpigotCore;
import org.apsarasmc.spigot.util.KeyUtil;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

public class SpigotPlayer implements ImplPlayer {
  @Nonnull
  protected final org.bukkit.entity.Player handle;

  @Nonnull
  protected final Audience adventure;

  public SpigotPlayer(final @Nonnull org.bukkit.entity.Player handle, Audience adventure) {
    this.handle = handle;
    this.adventure = Apsaras.injector().getInstance(BukkitAudiences.class).player(this.handle);
  }

  public SpigotPlayer(final @Nonnull org.bukkit.entity.Player handle) {
    this.handle = handle;
    if(SpigotCore.IS_PAPER) {
      this.adventure = handle;
    }else {
      this.adventure = Apsaras.injector().getInstance(BukkitAudiences.class).player(this.handle);
    }
  }

  @Nonnull
  public org.bukkit.entity.Player handle() {
    return handle;
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
  public String placeholder(ResourceKey key) {
    try {
      Class.forName("me.clip.placeholderapi.PlaceholderAPI");
      if(key.namespace().equals("papi")) {
        String holders = "%" + key.value() + "%";
        String result = PlaceholderAPI.setPlaceholders(
          handle,
          holders
        );
        if (!result.equals(holders)) {
          return result;
        }
      }
    } catch (ClassNotFoundException e) {
      //
    }
    return key.asString();
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

  @Override
  public @Nonnull Identity identity() {
    return Identity.identity(handle.getUniqueId());
  }

  @Override
  public boolean hasPermission(String name) {
    return handle.hasPermission(name);
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
