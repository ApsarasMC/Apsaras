package org.apsarasmc.apsaras.command.exception;

import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.exception.ComponentMessageException;

public class ArgumentParseException extends ComponentMessageException {
  public ArgumentParseException(Component message) {
    super(message);
  }
}
