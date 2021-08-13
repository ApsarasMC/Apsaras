package org.apsarasmc.apsaras.exception;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.util.ComponentMessageThrowable;

import javax.annotation.Nullable;

public class ComponentMessageException extends Exception implements ComponentMessageThrowable {
  private final Component message;

  public ComponentMessageException(final Component message) {
    super(PlainTextComponentSerializer.plainText().serialize(message));
    this.message = message;
  }

  public ComponentMessageException(final Component message, final Throwable cause) {
    super(PlainTextComponentSerializer.plainText().serialize(message), cause);
    this.message = message;
  }

  @Override
  public @Nullable Component componentMessage() {
    return message;
  }
}
