package org.apsarasmc.sponge.event.message;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.apsaras.event.EventContext;
import org.apsarasmc.apsaras.event.message.ChatEvent;
import org.apsarasmc.sponge.entity.SpongePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.entity.living.player.PlayerChatFormatter;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.message.PlayerChatEvent;

import javax.annotation.Nonnull;
import java.util.Optional;

public class SpongeChatEvent implements ChatEvent {
  private final EventContext context = EventContext.builder().build();
  private final PlayerChatEvent handle;

  public SpongeChatEvent(@Nonnull PlayerChatEvent handle) {
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
    return handle.chatFormatter().map(sponge -> {
      if (sponge instanceof ToSpongeChatFormatter) {
        return ((ToSpongeChatFormatter) sponge).apsaras;
      }
      return new ToApsarasChatFormatter(sponge);
    });
  }

  @Override
  public void setChatFormatter(@Nullable ChatEvent.ChatFormatter router) {
    if (router instanceof ToApsarasChatFormatter) {
      handle.setChatFormatter(((ToApsarasChatFormatter) router).sponge);
    } else {
      handle.setChatFormatter(new ToSpongeChatFormatter(this, router));
    }
  }

  @Override
  public String originalMessage() {
    return PlainTextComponentSerializer.plainText().serialize(handle.originalMessage());
  }

  @Override
  public String message() {
    return PlainTextComponentSerializer.plainText().serialize(handle.message());
  }

  @Override
  public void setMessage(@NotNull String message) {
    handle.setMessage(Component.text(message));
  }

  @Override
  public Player sender() {
    return new SpongePlayer((ServerPlayer) handle.source());
  }

  public static class ToApsarasChatFormatter implements ChatFormatter {
    private final PlayerChatFormatter sponge;

    public ToApsarasChatFormatter(PlayerChatFormatter sponge) {
      this.sponge = sponge;
    }

    @Override
    public Component format(ChatEvent event, Player receiver) {
      return sponge.format(
        ((SpongePlayer) event.sender()).handle,
        receiver,
        Component.text(event.message()),
        Component.text(event.originalMessage())).orElse(null);
    }
  }

  public static class ToSpongeChatFormatter implements PlayerChatFormatter {
    private final SpongeChatEvent event;
    private final ChatFormatter apsaras;

    public ToSpongeChatFormatter(SpongeChatEvent event, ChatFormatter apsaras) {
      this.event = event;
      this.apsaras = apsaras;
    }

    @Override
    public Optional< Component > format(ServerPlayer player, Audience target, Component message, Component originalMessage) {
      if (target instanceof ServerPlayer) {
        return Optional.ofNullable(
          apsaras.format(event, new SpongePlayer((ServerPlayer) target))
        );
      } else {
        return event.handle.originalChatFormatter().format(player, target, message, originalMessage);
      }
    }
  }
}
