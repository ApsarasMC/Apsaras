package org.apsarasmc.sponge.event;

import org.apsarasmc.plugin.event.EventTransfer;
import org.apsarasmc.plugin.event.lifecycle.ImplServerLoadEvent;
import org.apsarasmc.sponge.util.EventUtil;
import org.spongepowered.api.event.lifecycle.LoadedGameEvent;

import javax.inject.Singleton;

@Singleton
public class LifeCycleTransfer implements EventTransfer {
  @Override
  public void register() {
    EventUtil.listen(LoadedGameEvent.class, event -> new ImplServerLoadEvent());
  }
}
