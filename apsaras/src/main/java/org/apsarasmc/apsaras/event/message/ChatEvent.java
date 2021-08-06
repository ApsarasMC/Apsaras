package org.apsarasmc.apsaras.event.message;

import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.apsaras.event.Cancellable;
import org.apsarasmc.apsaras.event.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface ChatEvent extends Event, Cancellable {
  Optional< ChatEvent.ChatFormatter > chatFormatter();

  void setChatFormatter(@Nullable ChatEvent.ChatFormatter router);

  String originalMessage();

  String message();

  void setMessage(@Nonnull String message);

  Player sender();

  interface ChatFormatter {
    @Nullable
    Component format(ChatEvent event, Player receiver);
  }
}
