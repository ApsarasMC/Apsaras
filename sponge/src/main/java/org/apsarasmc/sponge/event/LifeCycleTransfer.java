package org.apsarasmc.sponge.event;

import io.leangen.geantyref.TypeToken;
import org.apsarasmc.plugin.event.EventTransfer;
import org.apsarasmc.plugin.event.lifecycle.ImplServerLifeEvent;
import org.apsarasmc.sponge.event.lifecycle.SpongeLoadPluginEvent;
import org.apsarasmc.sponge.util.EventUtil;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.event.lifecycle.LoadedGameEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;

import javax.inject.Singleton;

@Singleton
public class LifeCycleTransfer implements EventTransfer {
  @Override
  public void register() {
    EventUtil.listen(LoadedGameEvent.class, event -> new ImplServerLifeEvent.Enable());
    EventUtil.listen(
      new TypeToken<RegisterCommandEvent< Command.Raw>>(){},
      SpongeLoadPluginEvent.RegisterCommand::new);
  }
}
