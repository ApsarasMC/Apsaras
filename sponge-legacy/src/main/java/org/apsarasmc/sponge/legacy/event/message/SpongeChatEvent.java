package org.apsarasmc.sponge.legacy.event.message;

import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.apsaras.event.EventContext;
import org.apsarasmc.apsaras.event.message.ChatEvent;
import org.apsarasmc.sponge.legacy.entity.SpongePlayer;
import org.apsarasmc.sponge.legacy.util.TextComponentUtil;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class SpongeChatEvent implements ChatEvent {
  private final EventContext context = EventContext.builder().build();
  private final MessageChannelEvent.Chat handle;
  private ChatFormatter chatFormatter = null;

  public SpongeChatEvent(MessageChannelEvent.Chat handle) {
    this.handle = handle;
  }

  @Override
  public boolean cancelled() {
    return handle.isCancelled();
  }

  @Override
  public void cancelled(boolean cancel) {
    handle.setCancelled(cancel);
  }

  @Override
  public EventContext context() {
    return context;
  }

  @Override
  public Optional< ChatFormatter > chatFormatter() {
    if(chatFormatter == null){
      return Optional.empty();
    }else{
      return Optional.of(chatFormatter);
    }
  }

  @Override
  public void setChatFormatter(@Nullable ChatEvent.ChatFormatter router) {
    chatFormatter = router;
  }

  @Override
  public String originalMessage() {
    return handle.getRawMessage().toPlain();
  }

  @Override
  public String message() {
    return handle.getMessage().toPlain();
  }

  @Override
  public void setMessage(@Nonnull String message) {
    handle.setMessage(Text.of(message));
  }

  public void setMessage(@Nonnull Component message) {
    handle.setMessage(TextComponentUtil.toSponge(message));
  }

  @Override
  public Player sender() {
    Optional<org.spongepowered.api.entity.living.player.Player> playerOptional =  handle.getCause().first(org.spongepowered.api.entity.living.player.Player.class);
    return playerOptional.map(SpongePlayer::new).orElse(null);
  }

  public MessageChannelEvent.Chat handle() {
    return handle;
  }
}