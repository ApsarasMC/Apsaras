package org.apsarasmc.apsaras;

import com.google.inject.Injector;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.plugin.PluginManager;

import javax.annotation.Nonnull;

public class Apsaras {
  public static final String NAME = "apsaras";
  private static Injector injector; // Fill by reflection

  private Apsaras() {
    //
  }

  @Nonnull
  public static Game game() {
    return injector().getInstance(Game.class);
  }

  @Nonnull
  public static Injector injector() {
    if (Apsaras.injector == null) {
      throw new IllegalStateException("Apsaras has not been initialized!");
    }
    return Apsaras.injector;
  }

  @Nonnull
  public static Server server() {
    return injector().getInstance(Server.class);
  }

  @Nonnull
  public static EventManager eventManager() {
    return injector().getInstance(EventManager.class);
  }

  @Nonnull
  public static PluginManager pluginManager() {
    return injector().getInstance(PluginManager.class);
  }
}
