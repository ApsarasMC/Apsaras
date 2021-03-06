package org.apsarasmc.spigot.event.message;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.event.EventHandler;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.event.Order;
import org.apsarasmc.apsaras.event.message.ChatEvent;
import org.apsarasmc.plugin.ApsarasPluginContainer;
import org.apsarasmc.plugin.event.EventTransfer;
import org.apsarasmc.plugin.setting.ApsarasSetting;
import org.apsarasmc.spigot.entity.SpigotPlayer;
import org.apsarasmc.spigot.util.EventUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

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
    if(apsarasSetting.enableHandleChatFormatter) {
      EventUtil.listen(PlayerChatEvent.class, event -> new SpigotSyncChatEvent(event, new SpigotPlayer(event.getPlayer())));
    }else{
      EventUtil.listen(AsyncPlayerChatEvent.class, event -> new SpigotAsyncChatEvent(event, new SpigotPlayer(event.getPlayer())));
    }
  }

  @EventHandler (order = Order.POST)
  public void evalChatEvent(SpigotSyncChatEvent e) {
    if (e.cancelled()) {
      return;
    }
    Optional< ChatEvent.ChatFormatter > optional = e.chatFormatter();
    if (optional.isPresent()) {
      e.cancel();
      for (Player recipient : e.handle.getRecipients()) {
        SpigotPlayer receiver = new SpigotPlayer(recipient);
        Component component = optional.get().format(e, receiver);
        if(component != null){
          receiver.sendMessage(e.sender().identity(), component, MessageType.CHAT);
        }
      }
    }
  }
}
