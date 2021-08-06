package org.apsarasmc.spigot.event.message;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.apsarasmc.apsaras.event.EventHandler;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.event.Order;
import org.apsarasmc.apsaras.event.message.ChatEvent;
import org.apsarasmc.plugin.ApsarasPluginContainer;
import org.apsarasmc.plugin.setting.ApsarasSetting;
import org.apsarasmc.spigot.entity.SpigotPlayer;
import org.apsarasmc.plugin.event.EventTransfer;
import org.apsarasmc.spigot.util.EventUtil;
import org.apsarasmc.spigot.util.TextComponentUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class MessageTransfer implements EventTransfer {
  @Inject
  private ApsarasPluginContainer plugin;
  @Inject
  private EventManager eventManager;
  @Inject
  private ApsarasSetting apsarasSetting;

  @Override
  public void register() {
    eventManager.registerListeners(plugin, this);
    EventUtil.listen(AsyncPlayerChatEvent.class, event -> new SpigotChatEvent(event, new SpigotPlayer(event.getPlayer())));
  }

  @EventHandler(order = Order.POST)
  public void evalChatEvent(SpigotChatEvent e){
    if (e.cancelled() || !apsarasSetting.enableHandleChatFormatter) {
      return;
    }
    Optional< ChatEvent.ChatFormatter > optional =  e.chatFormatter();
    if(optional.isPresent()){
      e.cancel();
      for (Player recipient : e.handle.getRecipients()) {
        BaseComponent[] messageComponents = TextComponentUtil.toBukkit(optional.get().format(e, new SpigotPlayer(recipient)));
        recipient.spigot().sendMessage(ChatMessageType.CHAT, e.sender().uuid(), messageComponents);
      }
    }
  }
}
