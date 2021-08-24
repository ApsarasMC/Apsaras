package org.apsarasmc.plugin.scheduler;

import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.scheduler.SyncScheduler;
import org.apsarasmc.apsaras.tasker.SyncTasker;
import org.apsarasmc.plugin.ApsarasPluginContainer;
import org.quartz.SchedulerException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImplSyncScheduler extends ImplScheduler implements SyncScheduler {
  @Inject
  protected ImplSyncScheduler(ApsarasPluginContainer plugin, SyncTasker syncTasker) throws SchedulerException {
    super(plugin, syncTasker, "default-sync");
  }
}
