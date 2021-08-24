package org.apsarasmc.apsaras.command;

public interface CommandReader {
  String read(int length);
  String readString();

  String get(int length);
  String getString();

  int remain();
  void end();

  boolean ended();
}
