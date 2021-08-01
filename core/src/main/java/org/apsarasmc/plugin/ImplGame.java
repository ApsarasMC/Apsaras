package org.apsarasmc.plugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.apsarasmc.apsaras.Game;
import org.apsarasmc.apsaras.Server;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.plugin.PluginManager;
import org.apsarasmc.plugin.setting.ApsarasSetting;
import org.apsarasmc.plugin.util.StaticEntryUtil;

import javax.inject.Inject;

public class ImplGame implements Game {
  private final Injector injector;
  @Inject
  private PluginManager pluginManager;
  @Inject
  private EventManager eventManager;
  @Inject
  private ApsarasSetting setting;
  @Inject
  private BannerPrinter bannerPrinter;
  @Inject
  private Server server;
  @Inject
  private ApsarasPluginContainer self;

  public ImplGame(final Module module) {
    this.injector = Guice.createInjector(new ImplModule(binder ->
      binder.bind(Game.class).toInstance(this)
    ), module);
    StaticEntryUtil.applyInjector(ImplGame.class.getClassLoader(), this.injector);
    this.injector.injectMembers(this);
    this.pluginManager.addPlugin(self);
    if (this.setting.banner) {
      // I don't want to log this
      this.bannerPrinter.print(System.out);
    }
  }

  @Override
  public Server server() {
    return this.server;
  }

  @Override
  public PluginManager pluginManager() {
    return this.pluginManager;
  }

  @Override
  public EventManager eventManager() {
    return this.eventManager;
  }

  @Override
  public Injector injector() {
    return this.injector;
  }

  @Override
  public PluginContainer self() {
    return this.self;
  }
}
