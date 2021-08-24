package org.apsarasmc.apsaras.scheduler;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.builder.AbstractBuilder;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.tasker.Tasker;

public interface Scheduler {
  JobToken commit(ScheduledJob job);
  void cancel(JobToken token);

  static Builder builder(){
    return Apsaras.injector().getInstance(Builder.class);
  }

  interface Builder extends AbstractBuilder< Scheduler > {
    Builder plugin(PluginContainer plugin);
    Builder taskerService(Tasker tasker);
    Builder name(String name);
  }
}
