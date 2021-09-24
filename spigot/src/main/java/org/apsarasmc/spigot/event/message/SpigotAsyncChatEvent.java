package org.apsarasmc.spigot.event.message;

import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.apsaras.event.EventContext;
import org.apsarasmc.apsaras.event.message.ChatEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class SpigotAsyncChatEvent implements ChatEvent {
  public final AsyncPlayerChatEvent handle;
  private final EventContext context = EventContext.builder().build();
  private final Player sender;
  private final String originalMessage;
  private Optional< ChatFormatter > chatFormatter = Optional.empty();

  public SpigotAsyncChatEvent(AsyncPlayerChatEvent handle, Player sender) {
    this.handle = handle;
    this.sender = sender;
    this.originalMessage = handle.getMessage();
  }

  @Override
  public EventContext context() {
    return context;
  }

  @Override
  public Optional< ChatFormatter > chatFormatter() {
    return chatFormatter;
  }

  @Override
  public void setChatFormatter(@Nullable ChatFormatter router) {
    chatFormatter = Optional.ofNullable(router);
  }

  @Override
  public String originalMessage() {
    return originalMessage;
  }

  @Override
  public String message() {
    return handle.getMessage();
  }

  @Override
  public void setMessage(@Nonnull String message) {
    handle.setMessage(message);
  }

  @Override
  public Player sender() {
    return sender;
  }

  @Override
  public boolean cancelled() {
    return handle.isCancelled();
  }

  @Override
  public void cancelled(boolean cancel) {
    handle.setCancelled(cancel);
  }
}
