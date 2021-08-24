package org.apsarasmc.sponge.event;

import io.leangen.geantyref.TypeToken;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.plugin.event.EventTransfer;
import org.apsarasmc.plugin.event.lifecycle.ImplServerLifeEvent;
import org.apsarasmc.sponge.SpongeCore;
import org.apsarasmc.sponge.event.lifecycle.SpongeLoadPluginEvent;
import org.apsarasmc.sponge.util.EventUtil;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.event.EventListenerRegistration;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.lifecycle.LoadedGameEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicBoolean;

@Singleton
public class LifeCycleTransfer implements EventTransfer {
  @Inject
  private SpongeCore core;
  @Override
  public void register() {
    EventUtil.listen(LoadedGameEvent.class, event -> new ImplServerLifeEvent.Enable());
    SpongeLoadPluginEvent.RegisterCommand registerCommandEvent = new SpongeLoadPluginEvent.RegisterCommand();
    AtomicBoolean registerCommandEventPosted = new AtomicBoolean(false);
    Sponge.eventManager().registerListener(
      EventListenerRegistration.builder(new TypeToken<RegisterCommandEvent< Command.Raw>>(){})
        .order(Order.DEFAULT)
        .plugin(core.wrapper())
        .listener(event -> {
          if (!registerCommandEventPosted.get()) {
            registerCommandEventPosted.set(true);
            Apsaras.eventManager().post(registerCommandEvent);
          }
          registerCommandEvent.rawCommands().forEach((k,v)->{
            event.register(core.wrapper(), v, k);
          });
        }).build()
    );
    Sponge.eventManager().registerListener(
      EventListenerRegistration.builder(new TypeToken<RegisterCommandEvent< Command.Parameterized>>(){})
        .order(Order.DEFAULT)
        .plugin(core.wrapper())
        .listener(event -> {
          if (!registerCommandEventPosted.get()) {
            registerCommandEventPosted.set(true);
            Apsaras.eventManager().post(registerCommandEvent);
          }
          registerCommandEvent.parameterizedCommands().forEach((k,v)->{
            event.register(core.wrapper(), v, k);
          });
        }).build()
    );
  }
}
