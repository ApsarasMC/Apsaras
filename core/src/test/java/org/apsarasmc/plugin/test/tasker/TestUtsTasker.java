package org.apsarasmc.plugin.test.tasker;

import org.apsarasmc.apsaras.tasker.UtsTasker;
import org.apsarasmc.plugin.ApsarasPluginContainer;
import org.apsarasmc.plugin.tasker.ImplTasker;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TestUtsTasker extends ImplTasker implements UtsTasker {
  @Inject
  public TestUtsTasker(@Nonnull ApsarasPluginContainer plugin) {
    super(plugin, 5, "sync");
  }
}
