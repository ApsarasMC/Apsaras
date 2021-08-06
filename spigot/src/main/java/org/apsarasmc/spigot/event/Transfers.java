package org.apsarasmc.spigot.event;

import org.apsarasmc.plugin.event.EventTransfer;
import org.apsarasmc.spigot.event.message.MessageTransfer;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Transfers implements EventTransfer {
  @Inject
  private MessageTransfer message;
  @Inject
  private LifeCycleTransfer lifeCycle;

  @Override
  public void register() {
    message.register();
    lifeCycle.register();
  }
}
