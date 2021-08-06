package org.apsarasmc.apsaras.entity;

import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import org.apsarasmc.apsaras.util.Named;
import org.apsarasmc.apsaras.util.ResourceKeyed;

import javax.annotation.Nonnull;

public interface Entity extends Named, ResourceKeyed, Identified, Identity {
  @Nonnull
  default Identity identity() {
    return this;
  }
}
