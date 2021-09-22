package org.apsarasmc.sponge.legacy.event;

import org.apsarasmc.plugin.event.EventTransfer;
import org.apsarasmc.sponge.legacy.event.message.MessageTransfer;

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
