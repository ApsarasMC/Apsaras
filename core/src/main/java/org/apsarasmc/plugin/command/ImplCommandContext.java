package org.apsarasmc.plugin.command;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.apsarasmc.apsaras.command.CommandArgument;
import org.apsarasmc.apsaras.command.CommandContext;
import org.apsarasmc.apsaras.command.CommandSender;
import org.apsarasmc.apsaras.entity.PermissionCheckable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class ImplCommandContext implements CommandContext {
  private final CommandSender sender;
  private final Map<CommandArgument<?>, Object> arguments;

  public ImplCommandContext(CommandSender sender, Map< CommandArgument< ? >, Object > arguments) {
    this.sender = sender;
    this.arguments = arguments;
  }

  @Override
  public CommandSender sender() {
    return sender;
  }

  @Override
  @SuppressWarnings("unchecked")
  public < T > T get(CommandArgument< T > argument) {
    return (T) arguments.get(argument);
  }

  @Override
  public < T > void put(CommandArgument< T > argument, T value) {
    arguments.put(argument,value);
  }

  @Override
  public boolean hasPermission(String name) {
    if(sender instanceof PermissionCheckable){
      return ((PermissionCheckable) sender).hasPermission(name);
    }
    return true;
  }

  @Override
  public void showBossBar(final @Nonnull BossBar bar) {
    sender.showBossBar(bar);
  }

  @Override
  public void hideBossBar(final @Nonnull BossBar bar) {
    sender.hideBossBar(bar);
  }

  @Override
  public void sendMessage(@Nonnull Identity source, @Nonnull Component message, @Nonnull MessageType type) {
    sender.sendMessage(source, message, type);
  }

  @Override
  public void playSound(@Nonnull Sound sound) {
    sender.playSound(sound);
  }

  @Override
  public void playSound(@Nonnull Sound sound, double x, double y, double z) {
    sender.playSound(sound, x, y, z);
  }

  @Override
  public void stopSound(@Nonnull Sound sound) {
    sender.stopSound(sound);
  }

  @Override
  public void playSound(@Nonnull Sound sound, @Nonnull Sound.Emitter emitter) {
    sender.playSound(sound, emitter);
  }

  @Override
  public void stopSound(@Nonnull SoundStop stop) {
    sender.stopSound(stop);
  }

  @Override
  public void sendActionBar(@Nonnull Component message) {
    sender.sendActionBar(message);
  }

  @Override
  public void sendPlayerListHeaderAndFooter(@Nonnull Component header, @Nonnull Component footer) {
    sender.sendPlayerListHeaderAndFooter(header, footer);
  }

  @Override
  public void showTitle(@Nonnull Title title) {
    sender.showTitle(title);
  }

  @Override
  public void clearTitle() {
    sender.clearTitle();
  }

  @Override
  public void resetTitle() {
    sender.resetTitle();
  }

  @Override
  public void openBook(@Nonnull Book book) {
    sender.openBook(book);
  }

  public static class Builder implements CommandContext.Builder {
    private CommandSender sender;
    private final Map<CommandArgument<?>, Object> arguments = new HashMap<>();

    @Override
    public CommandContext build() {
      return new ImplCommandContext(sender, arguments);
    }

    @Override
    public CommandContext.Builder sender(CommandSender sender) {
      this.sender = sender;
      return this;
    }

    @Override
    public < T > CommandContext.Builder argument(CommandArgument< T > argument, T value) {
      this.arguments.put(argument, value);
      return this;
    }
  }
}
