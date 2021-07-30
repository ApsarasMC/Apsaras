package org.apsarasmc.apsaras.event;

public interface Cancellable extends Event {
  boolean isCancelled();

  void setCancelled(boolean cancel);
}
