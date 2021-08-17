package org.apsarasmc.spigot.event;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.plugin.event.EventTransfer;
import org.apsarasmc.plugin.event.lifecycle.ImplServerLifeEvent;
import org.apsarasmc.spigot.util.EventUtil;
import org.bukkit.event.server.ServerLoadEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LifeCycleTransfer implements EventTransfer {
  @Inject
  private EventManager eventManager;
  public static boolean postEnable = false;
  @Override
  public void register() {
    try {
      Class.forName("org.bukkit.event.server.ServerLoadEvent");
      EventUtil.listen(ServerLoadEvent.class, event -> {
        if (event.getType() == ServerLoadEvent.LoadType.RELOAD) {
          Apsaras.server().logger().warn("Apsaras was never designed with reload.");
          Apsaras.server().logger().warn("It may cause serious problem.");
        }
        return new ImplServerLifeEvent.Enable();
      });
    } catch (ClassNotFoundException ignore) {
      LifeCycleTransfer.postEnable = true;
    }
  }
}
