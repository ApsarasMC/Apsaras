package org.apsarasmc.apsaras.command;

import org.apsarasmc.apsaras.entity.Player;

public final class Checkers {
  private static final Checkable PLAYER = sender -> sender instanceof Player;
  private static final Checkable CONSOLE = sender -> !(sender instanceof Player);

  public static Checkable player() {
    return Checkers.PLAYER;
  }

  public static Checkable console() {
    return Checkers.CONSOLE;
  }
}
