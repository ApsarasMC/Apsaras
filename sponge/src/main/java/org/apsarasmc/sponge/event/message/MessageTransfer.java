package org.apsarasmc.sponge.event.message;

import org.apsarasmc.plugin.event.EventTransfer;
import org.apsarasmc.sponge.util.EventUtil;
import org.spongepowered.api.event.message.PlayerChatEvent;

public class MessageTransfer implements EventTransfer {
  @Override
  public void register() {
    EventUtil.listen(PlayerChatEvent.class, SpongeChatEvent::new);
  }
}
