package org.apsarasmc.plugin.scheduler;

import org.apsarasmc.apsaras.scheduler.UtsScheduler;
import org.apsarasmc.apsaras.tasker.UtsTasker;
import org.apsarasmc.plugin.ApsarasPluginContainer;
import org.quartz.SchedulerException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImplUtsScheduler extends ImplScheduler implements UtsScheduler {
  @Inject
  protected ImplUtsScheduler(ApsarasPluginContainer plugin, UtsTasker utsTasker) throws SchedulerException {
    super(plugin, utsTasker, "default-uts");
  }
}
