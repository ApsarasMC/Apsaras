package org.apsarasmc.plugin.test;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.apsaras.tasker.SyncTasker;
import org.apsarasmc.apsaras.tasker.UtsTasker;
import org.apsarasmc.plugin.test.fake.FakePlayerProvider;
import org.apsarasmc.plugin.test.tasker.TestSyncTasker;
import org.apsarasmc.plugin.test.tasker.TestUtsTasker;

public class TestModule implements Module {
  private final Module applyModule;

  public TestModule(Module module) {
    this.applyModule = module;
  }

  @Override
  public void configure(Binder binder) {
    applyModule.configure(binder);
    binder.bind(Player.class).toProvider(FakePlayerProvider.class);

    binder.bind(SyncTasker.class).to(TestSyncTasker.class);
    binder.bind(UtsTasker.class).to(TestUtsTasker.class);
  }
}
