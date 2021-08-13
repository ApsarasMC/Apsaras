package org.apsarasmc.apsaras.command;

public interface CommandReader {
  String read(int length);
  String readString();
  int remain();
  void end();

  boolean ended();
}
