package org.apsarasmc.plugin.test;

import com.google.inject.Binder;
import com.google.inject.Module;

public class TestModule implements Module {
  private final Module applyModule;

  public TestModule(Module module) {
    this.applyModule = module;
  }

  @Override
  public void configure(Binder binder) {
    applyModule.configure(binder);
  }
}
