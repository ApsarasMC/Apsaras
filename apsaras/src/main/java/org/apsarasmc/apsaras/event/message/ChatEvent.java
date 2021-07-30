package org.apsarasmc.apsaras.event.message;

import net.kyori.adventure.text.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface ChatEvent {
  String originalChatFormatter();

  Optional< String > chatFormatter();

  void setChatFormatter(@Nullable String router);

  String originalMessage();

  Component message();

  void setMessage(@Nonnull String message);
}
