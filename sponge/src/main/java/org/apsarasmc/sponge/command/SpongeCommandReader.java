package org.apsarasmc.sponge.command;

import org.apsarasmc.apsaras.command.CommandReader;
import org.spongepowered.api.command.exception.ArgumentParseException;
import org.spongepowered.api.command.parameter.ArgumentReader;

public class SpongeCommandReader implements CommandReader {
  private final ArgumentReader.Mutable handle;
  public SpongeCommandReader(final ArgumentReader.Mutable handle){
    this.handle = handle;
  }

  @Override
  public String read(int length) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      builder.append(handle.parseChar());
    }
    return builder.toString();
  }

  @Override
  public String readString() {
    try {
      return handle.parseString();
    } catch (ArgumentParseException e) {
      return read(remain());
    }
  }

  @Override
  public String get(int length) {
    return handle.remaining().substring(0,length);
  }

  @Override
  public String getString() {
    try {
      return handle.parseString();
    } catch (ArgumentParseException e) {
      return get(remain());
    }
  }

  @Override
  public int remain() {
    return handle.remainingLength();
  }

  @Override
  public void end() {
    //
  }

  @Override
  public boolean ended() {
    return remain() <= 0;
  }
}
