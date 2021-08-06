package org.apsarasmc.apsaras.event;

public interface Cancellable extends Event {
  boolean cancelled();

  void cancelled(boolean cancel);

  default void cancel() {
    cancelled(true);
  }
}
