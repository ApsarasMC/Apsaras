package org.apsarasmc.spigot.event;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.plugin.event.lifecycle.ImplServerLoadEvent;
import org.apsarasmc.spigot.util.EventUtil;
import org.bukkit.event.server.ServerLoadEvent;

import javax.inject.Singleton;

@Singleton
public class LifeCycleHandler implements EventHandler {
  @Override
  public void register() {
    EventUtil.listen(ServerLoadEvent.class, event -> {
      if (event.getType() == ServerLoadEvent.LoadType.RELOAD) {
        Apsaras.server().logger().warn("Apsaras was never designed with reload.");
        Apsaras.server().logger().warn("It may cause serious problem.");
      }
      return new ImplServerLoadEvent();
    });
  }
}
