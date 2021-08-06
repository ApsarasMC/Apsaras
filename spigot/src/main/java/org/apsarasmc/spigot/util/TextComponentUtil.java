package org.apsarasmc.spigot.util;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.plugin.setting.ApsarasSetting;

import java.util.EnumMap;
import java.util.Map;

public class TextComponentUtil {
  private static final ApsarasSetting apsarasSetting = Apsaras.injector().getInstance(ApsarasSetting.class);
  private static final BungeeComponentSerializer serializer = apsarasSetting.messageLegacy
    ? BungeeComponentSerializer.legacy()
    : BungeeComponentSerializer.get();
  public static final Map<MessageType, ChatMessageType> messageToBukkit
    = new EnumMap<>(MessageType.class);
  static {
    messageToBukkit.put(MessageType.CHAT, ChatMessageType.CHAT);
    messageToBukkit.put(MessageType.SYSTEM, ChatMessageType.SYSTEM);
  }

  TextComponentUtil() {
    //
  }

  public static ChatMessageType toBukkit(MessageType adventure){
    return messageToBukkit.get(adventure);
  }

  public static BaseComponent[] toBukkit(net.kyori.adventure.text.Component adventure) {
    return serializer.serialize(adventure);
  }

  public static Component toAdventure(BaseComponent... bungee){
    return serializer.deserialize(bungee);
  }
}
