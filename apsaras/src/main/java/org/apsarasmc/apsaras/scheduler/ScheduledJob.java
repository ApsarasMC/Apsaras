package org.apsarasmc.apsaras.scheduler;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.builder.AbstractBuilder;
import org.apsarasmc.apsaras.plugin.PluginContainer;

import java.util.Collection;

public interface ScheduledJob {
  Runnable command();

  PluginContainer plugin();

  Collection<TimeCondition> timeConditions();

  static Builder builder(){
    return Apsaras.injector().getInstance(Builder.class);
  }

  interface Builder extends AbstractBuilder<ScheduledJob> {
    Builder command(Runnable command);
    Builder plugin(PluginContainer pluginContainer);
    Builder timeCondition(TimeCondition timeCondition);
  }
}
