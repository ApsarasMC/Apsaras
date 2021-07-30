package org.apsarasmc.spigot;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.apsarasmc.spigot.util.EventUtil;

public class SpigotModule implements Module {
  private final Module applyModule;

  public SpigotModule(Module module) {
    this.applyModule = module;
  }

  @Override
  public void configure(Binder binder) {
    applyModule.configure(binder);
    binder.requestStaticInjection(EventUtil.class);
  }
}
