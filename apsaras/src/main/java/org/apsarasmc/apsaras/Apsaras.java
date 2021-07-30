package org.apsarasmc.apsaras;

import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.plugin.PluginManager;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class Apsaras {
  public static final String NAME = "apsaras";
  @Inject
  private static Game game;

  private Apsaras() {
    //
  }

  @Nonnull
  public static Game game() {
    if (Apsaras.game == null) {
      throw new IllegalStateException("Apsaras has not been initialized!");
    }
    return Apsaras.game;
  }

  @Nonnull
  public static Injector injector() {
    return Apsaras.game().injector();
  }

  @Nonnull
  public static Server server() {
    return Apsaras.game().server();
  }

  @Nonnull
  public static EventManager eventManager() {
    return Apsaras.game().eventManager();
  }

  @Nonnull
  public static PluginManager pluginManager() {
    return Apsaras.game().pluginManager();
  }
}
