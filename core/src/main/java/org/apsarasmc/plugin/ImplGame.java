package org.apsarasmc.plugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.apsarasmc.apsaras.Game;
import org.apsarasmc.apsaras.Server;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.plugin.PluginManager;
import org.apsarasmc.plugin.aop.ImplInjector;
import org.apsarasmc.plugin.event.lifecycle.ImplServerLifeEvent;
import org.apsarasmc.plugin.setting.ApsarasSetting;
import org.apsarasmc.plugin.util.StaticEntryUtil;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
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
      binder.bind(ImplGame.class).toInstance(this)
    ), module);
    StaticEntryUtil.applyInjector(ImplGame.class.getClassLoader(), new ImplInjector(this.injector));
    this.injector.injectMembers(this);
    this.pluginManager.addPlugin(self);
  }

  public void enable() {
    if (this.setting.banner) {
      // I don't want to log this
      this.bannerPrinter.print(System.out);
    }
    pluginManager.load();
    pluginManager.enable();
    eventManager.post(new ImplServerLifeEvent.Load());
  }

  public void disable() {
    eventManager.post(new ImplServerLifeEvent.Disable());
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
  public org.apsarasmc.apsaras.aop.Injector injector() {
    return new ImplInjector(this.injector);
  }

  @Override
  public PluginContainer self() {
    return this.self;
  }
}
