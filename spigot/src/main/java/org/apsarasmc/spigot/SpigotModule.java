package org.apsarasmc.spigot;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.apsarasmc.apsaras.entity.Player;
import org.apsarasmc.apsaras.tasker.SyncTasker;
import org.apsarasmc.apsaras.tasker.UtsTasker;
import org.apsarasmc.spigot.entity.SpigotPlayer;
import org.apsarasmc.spigot.tasker.SpigotSyncTasker;
import org.apsarasmc.spigot.tasker.SpigotUtsTasker;
import org.apsarasmc.spigot.util.EventUtil;

public class SpigotModule implements Module {
  private final Module applyModule;

  public SpigotModule(Module module) {
    this.applyModule = module;
  }

  @Override
  public void configure(Binder binder) {
    applyModule.configure(binder);
    // entity
    binder.bind(Player.Factory.class).to(SpigotPlayer.Factory.class);

    binder.bind(SyncTasker.class).to(SpigotSyncTasker.class);
    binder.bind(UtsTasker.class).to(SpigotUtsTasker.class);

    binder.requestStaticInjection(EventUtil.class);
  }
}
