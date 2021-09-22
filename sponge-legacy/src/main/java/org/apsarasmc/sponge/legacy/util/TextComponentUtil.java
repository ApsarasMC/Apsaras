package org.apsarasmc.sponge.legacy.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.spongeapi.SpongeComponentSerializer;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class TextComponentUtil {
  private static final SpongeComponentSerializer serializer = SpongeComponentSerializer.get();

  TextComponentUtil() {
    //
  }

  public static Text toSponge(Component adventure) {
    return serializer.serialize(adventure);
  }

  public static Component toAdventure(Text sponge) {
    return serializer.deserialize(sponge);
  }

  public static Optional<Text> toSponge(Optional<Component> adventure) {
    if(adventure.isPresent()){
      return Optional.of(toSponge(adventure.get()));
    }else{
      return Optional.empty();
    }
  }

  public static Optional<Component> toAdventure(Optional<Text> sponge) {
    if(sponge.isPresent()){
      return Optional.of(toAdventure(sponge.get()));
    }else{
      return Optional.empty();
    }
  }
}
