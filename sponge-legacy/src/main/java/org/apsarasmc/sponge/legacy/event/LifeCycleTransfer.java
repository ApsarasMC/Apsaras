package org.apsarasmc.sponge.legacy.event;

import com.google.common.reflect.TypeToken;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.command.Command;
import org.apsarasmc.plugin.event.EventTransfer;
import org.apsarasmc.plugin.event.lifecycle.ImplServerLifeEvent;
import org.apsarasmc.sponge.legacy.SpongeCore;
import org.apsarasmc.sponge.legacy.event.lifecycle.SpongeLoadPluginEvent;
import org.apsarasmc.sponge.legacy.util.EventUtil;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameLoadCompleteEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicBoolean;

@Singleton
public class LifeCycleTransfer implements EventTransfer {
  @Inject
  private SpongeCore core;
  @Override
  public void register() {
    EventUtil.listen(GameLoadCompleteEvent.class, event -> new ImplServerLifeEvent.Enable());
    Sponge.getEventManager().registerListener(
      core.wrapperInstance(),
      GameInitializationEvent.class,
      Order.DEFAULT,
      event -> {
        Apsaras.eventManager().post(new SpongeLoadPluginEvent.RegisterCommand());
      }
    );
  }
}
