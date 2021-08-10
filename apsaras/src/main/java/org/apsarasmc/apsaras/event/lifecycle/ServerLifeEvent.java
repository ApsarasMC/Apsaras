package org.apsarasmc.apsaras.event.lifecycle;

public interface ServerLifeEvent extends LifeCycleEvent {
  interface Load extends ServerLifeEvent{

  }

  interface Enable extends ServerLifeEvent{

  }

  interface Disable extends ServerLifeEvent{

  }
}
