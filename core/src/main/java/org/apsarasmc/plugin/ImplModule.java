package org.apsarasmc.plugin;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.apsarasmc.apsaras.Game;
import org.apsarasmc.apsaras.Server;
import org.apsarasmc.apsaras.command.CommandResult;
import org.apsarasmc.apsaras.config.ConfigService;
import org.apsarasmc.apsaras.event.EventContextKey;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.plugin.PluginDepend;
import org.apsarasmc.apsaras.plugin.PluginManager;
import org.apsarasmc.apsaras.plugin.PluginMeta;
import org.apsarasmc.apsaras.plugin.PluginUrls;
import org.apsarasmc.apsaras.scheduler.SchedulerService;
import org.apsarasmc.apsaras.util.ResourceKey;
import org.apsarasmc.plugin.command.ImplCommandResult;
import org.apsarasmc.plugin.config.ImplConfigService;
import org.apsarasmc.plugin.event.ImplEventContextKey;
import org.apsarasmc.plugin.event.ImplEventManager;
import org.apsarasmc.plugin.plugin.ImplPluginDepend;
import org.apsarasmc.plugin.plugin.ImplPluginManager;
import org.apsarasmc.plugin.plugin.ImplPluginMeta;
import org.apsarasmc.plugin.plugin.ImplPluginUrls;
import org.apsarasmc.plugin.scheduler.ImplSchedulerService;
import org.apsarasmc.plugin.setting.ApsarasSetting;
import org.apsarasmc.plugin.setting.ImplSettingProvider;
import org.apsarasmc.plugin.util.ImplResourceKey;

public class ImplModule implements Module {
  private final Module applyModule;

  public ImplModule(Module module) {
    this.applyModule = module;
  }

  @Override
  public void configure(Binder binder) {
    applyModule.configure(binder);
    binder.bind(Game.class).to(ImplGame.class);
    // command
    binder.bind(CommandResult.Builder.class).to(ImplCommandResult.Builder.class);
    // server
    binder.bind(Server.class).to(ImplServer.class);
    // plugin
    binder.bind(PluginManager.class).to(ImplPluginManager.class);
    binder.bind(PluginDepend.Builder.class).to(ImplPluginDepend.Builder.class);
    binder.bind(PluginMeta.Builder.class).to(ImplPluginMeta.Builder.class);
    binder.bind(PluginUrls.Builder.class).to(ImplPluginUrls.Builder.class);
    // scheduler
    binder.bind(SchedulerService.Factory.class).to(ImplSchedulerService.Factory.class);
    // setting
    binder.bind(ApsarasSetting.class).toProvider(ImplSettingProvider.class);
    binder.bind(ConfigService.Factory.class).to(ImplConfigService.Factory.class);
    // util
    binder.bind(ResourceKey.Factory.class).to(ImplResourceKey.Factory.class);
    // event
    binder.bind(EventManager.class).to(ImplEventManager.class);
    binder.bind(EventContextKey.Builder.class).to(ImplEventContextKey.Builder.class);
  }
}
