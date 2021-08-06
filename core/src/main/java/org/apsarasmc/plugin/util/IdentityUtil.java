package org.apsarasmc.plugin.util;

import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;

import javax.annotation.Nullable;
import java.util.UUID;

public class IdentityUtil {
  private IdentityUtil() {
    //
  }

  public static Identity toIdentity(@Nullable UUID uuid) {
    if (uuid == null) {
      return null;
    }
    return Identity.identity(uuid);
  }

  public static Identity toIdentity(@Nullable Identified identified) {
    if (identified == null) {
      return null;
    }
    return identified.identity();
  }
}
