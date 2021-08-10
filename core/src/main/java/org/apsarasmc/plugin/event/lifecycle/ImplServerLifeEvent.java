package org.apsarasmc.plugin.event.lifecycle;

import org.apsarasmc.apsaras.event.EventContext;
import org.apsarasmc.apsaras.event.lifecycle.ServerLifeEvent;

public abstract class ImplServerLifeEvent implements ServerLifeEvent {
  private final EventContext context = EventContext.builder().build();

  @Override
  public EventContext context() {
    return this.context;
  }

  public static class Load extends ImplServerLifeEvent implements ServerLifeEvent.Load{

  }

  public static class Enable extends ImplServerLifeEvent implements ServerLifeEvent.Enable{

  }

  public static class Disable extends ImplServerLifeEvent implements ServerLifeEvent.Disable{

  }

}
