package org.apsarasmc.apsaras.event;

public enum Order {
  PRE(-4),
  AFTER_PRE(-3),
  FIRST(-2),
  EARLY(-1),
  DEFAULT(0),
  LATE(1),
  LAST(2),
  BEFORE_POST(3),
  POST(4);

  private final int value;

  Order(final int value) {
    this.value = value;
  }

  public int order() {
    return value;
  }
}
