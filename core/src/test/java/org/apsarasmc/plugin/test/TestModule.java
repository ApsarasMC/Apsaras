package org.apsarasmc.plugin.test;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.plugin.test.fake.FakePlayerProvider;

public class TestModule implements Module {
  private final Module applyModule;

  public TestModule(Module module) {
    this.applyModule = module;
  }

  @Override
  public void configure(Binder binder) {
    applyModule.configure(binder);
    binder.bind(Player.class).toProvider(FakePlayerProvider.class);
  }
}
