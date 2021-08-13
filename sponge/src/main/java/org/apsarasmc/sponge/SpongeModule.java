package org.apsarasmc.sponge;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.sponge.entity.SpongePlayer;
import org.apsarasmc.sponge.util.EventUtil;

public class SpongeModule implements Module {
  private final Module applyModule;

  public SpongeModule(Module module) {
    this.applyModule = module;
  }

  @Override
  public void configure(Binder binder) {
    applyModule.configure(binder);
    binder.bind(Player.Factory.class).to(SpongePlayer.Factory.class);
    binder.requestStaticInjection(EventUtil.class);
  }
}
