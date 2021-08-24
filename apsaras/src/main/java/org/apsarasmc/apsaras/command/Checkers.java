package org.apsarasmc.apsaras.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apsarasmc.apsaras.entity.PermissionCheckable;
import org.apsarasmc.apsaras.entity.Player;

public final class Checkers {
  private static final Checkable PLAYER = sender -> sender instanceof Player;
  private static final Checkable CONSOLE = sender -> !(sender instanceof Player);

  public static Checkable player() {
    return Checkers.PLAYER;
  }

  public static Checkable permission(final String permission) {
    return sender -> {
      if(sender instanceof PermissionCheckable) {
        return ((PermissionCheckable) sender).hasPermission(permission);
      }else {
        return true;
      }
    };
  }

  public static Checkable console() {
    return Checkers.CONSOLE;
  }
}
