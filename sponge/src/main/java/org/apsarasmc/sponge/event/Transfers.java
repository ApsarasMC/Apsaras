package org.apsarasmc.sponge.event;

import org.apsarasmc.plugin.event.EventTransfer;
import org.apsarasmc.sponge.event.message.MessageTransfer;

import javax.inject.Inject;

public class Transfers implements EventTransfer {
  @Inject
  private MessageTransfer messageTransfer;
  @Inject
  private LifeCycleTransfer lifeCycleHandler;

  @Override
  public void register() {
    messageTransfer.register();
    lifeCycleHandler.register();
  }
}
