package org.apsarasmc.sponge.util;


import javax.annotation.Nonnull;

public class PriorityUtil {
  private PriorityUtil() {
    //
  }

  @Nonnull
  public static org.spongepowered.api.event.Order of(org.apsarasmc.apsaras.event.Order order) {
    return org.spongepowered.api.event.Order.values()[ order.ordinal() ];
  }

  @Nonnull
  public static org.apsarasmc.apsaras.event.Order of(org.spongepowered.api.event.Order order) {
    return org.apsarasmc.apsaras.event.Order.values()[ order.ordinal() ];
  }
}
