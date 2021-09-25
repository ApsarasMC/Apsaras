package org.apsarasmc.plugin.test.tasker;

import org.apsarasmc.apsaras.tasker.SyncTasker;
import org.apsarasmc.plugin.ApsarasPluginContainer;
import org.apsarasmc.plugin.tasker.ImplTasker;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TestSyncTasker extends ImplTasker implements SyncTasker {
  @Inject
  public TestSyncTasker(@Nonnull ApsarasPluginContainer plugin) {
    super(plugin, 1, "sync");
  }

  @Override
  public boolean isSyncThread() {
    return true;
  }
}
