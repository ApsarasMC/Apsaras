package org.apsarasmc.plugin.command;

import org.apsarasmc.apsaras.command.CommandReader;

public class ImplCommandReader implements CommandReader {
  private final String context;
  private int offset = 0;
  boolean end = false;
  public ImplCommandReader(final String context){
    this.context = context;
  }
  @Override
  public String read(int length) {
    String result = context.substring(offset, offset + length);
    offset += length;
    return result;
  }

  @Override
  public String readString() {
    int pos = context.indexOf(' ', offset);
    String result;
    if(pos == -1){
      result =  context.substring(offset);
      offset = context.length();
      end();
    }else {
      result = context.substring(offset, pos);
      offset = pos+1;
    }
    return result;
  }

  @Override
  public int remain() {
    return context.length() - offset;
  }

  @Override
  public void end() {
    end = true;
  }

  @Override
  public boolean ended() {
    return end;
  }
}
