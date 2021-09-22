package org.apsarasmc.sponge.legacy.event.message;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.event.Order;
import org.apsarasmc.plugin.event.EventTransfer;
import org.apsarasmc.plugin.setting.ApsarasSetting;
import org.apsarasmc.sponge.legacy.util.EventUtil;
import org.apsarasmc.sponge.legacy.util.TextComponentUtil;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.chat.ChatTypes;

import javax.inject.Inject;

public class MessageTransfer implements EventTransfer {
  @Inject
  private ApsarasSetting setting;
  @Override
  public void register() {
    EventUtil.listen(MessageChannelEvent.Chat.class, SpongeChatEvent::new);
    if (setting.enableHandleChatFormatter) {
      Apsaras.eventManager().registerListener(Apsaras.game().self(), SpongeChatEvent.class, Order.POST, event -> {
        if(event.chatFormatter().isPresent() && !event.cancelled()){
          for (Player onlinePlayer : Sponge.getServer().getOnlinePlayers()) {
            onlinePlayer.sendMessage(ChatTypes.CHAT, TextComponentUtil.toSponge(
              event.chatFormatter().get().format(event, event.sender())
            ));
          }
          event.cancelled(true);
        }
      });
    }
  }
}
