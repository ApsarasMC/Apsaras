package org.apsarasmc.sponge;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.apsaras.tasker.SyncTasker;
import org.apsarasmc.apsaras.tasker.UtsTasker;
import org.apsarasmc.sponge.entity.SpongePlayer;
import org.apsarasmc.sponge.tasker.SpongeSyncTasker;
import org.apsarasmc.sponge.tasker.SpongeUtsTasker;
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

    binder.bind(SyncTasker.class).to(SpongeSyncTasker.class);
    binder.bind(UtsTasker.class).to(SpongeUtsTasker.class);

    binder.requestStaticInjection(EventUtil.class);
  }
}
