package org.apsarasmc.plugin;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.apsarasmc.apsaras.Game;
import org.apsarasmc.apsaras.Server;
import org.apsarasmc.apsaras.command.Checkable;
import org.apsarasmc.apsaras.command.Command;
import org.apsarasmc.apsaras.command.CommandContext;
import org.apsarasmc.apsaras.command.CommandResult;
import org.apsarasmc.apsaras.command.arguments.*;
import org.apsarasmc.apsaras.config.ConfigService;
import org.apsarasmc.apsaras.event.EventContextKey;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.plugin.PluginDepend;
import org.apsarasmc.apsaras.plugin.PluginManager;
import org.apsarasmc.apsaras.plugin.PluginMeta;
import org.apsarasmc.apsaras.plugin.PluginUrls;
import org.apsarasmc.apsaras.scheduler.*;
import org.apsarasmc.apsaras.tasker.Tasker;
import org.apsarasmc.apsaras.util.ResourceKey;
import org.apsarasmc.plugin.command.ImplCommand;
import org.apsarasmc.plugin.command.ImplCommandContext;
import org.apsarasmc.plugin.command.ImplCommandResult;
import org.apsarasmc.plugin.command.MultiCheckable;
import org.apsarasmc.plugin.command.arguments.*;
import org.apsarasmc.plugin.config.ImplConfigService;
import org.apsarasmc.plugin.event.ImplEventContextKey;
import org.apsarasmc.plugin.event.ImplEventManager;
import org.apsarasmc.plugin.plugin.ImplPluginDepend;
import org.apsarasmc.plugin.plugin.ImplPluginManager;
import org.apsarasmc.plugin.plugin.ImplPluginMeta;
import org.apsarasmc.plugin.plugin.ImplPluginUrls;
import org.apsarasmc.plugin.scheduler.*;
import org.apsarasmc.plugin.tasker.ImplTasker;
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
    binder.bind(BooleanArgument.Builder.class).to(ImplBooleanArgument.Builder.class);
    binder.bind(DoubleArgument.Builder.class).to(ImplDoubleArgument.Builder.class);
    binder.bind(FloatArgument.Builder.class).to(ImplFloatArgument.Builder.class);
    binder.bind(IntegerArgument.Builder.class).to(ImplIntegerArgument.Builder.class);
    binder.bind(LongArgument.Builder.class).to(ImplLongArgument.Builder.class);
    binder.bind(StringArgument.Builder.class).to(ImplStringArgument.Builder.class);
    binder.bind(Command.Builder.class).to(ImplCommand.Builder.class);
    binder.bind(CommandContext.Builder.class).to(ImplCommandContext.Builder.class);
    binder.bind(CommandResult.Builder.class).to(ImplCommandResult.Builder.class);
    binder.bind(Checkable.Builder.class).to(MultiCheckable.Builder.class);
    // server
    binder.bind(Server.class).to(ImplServer.class);
    // plugin
    binder.bind(PluginManager.class).to(ImplPluginManager.class);
    binder.bind(PluginDepend.Builder.class).to(ImplPluginDepend.Builder.class);
    binder.bind(PluginMeta.Builder.class).to(ImplPluginMeta.Builder.class);
    binder.bind(PluginUrls.Builder.class).to(ImplPluginUrls.Builder.class);
    // scheduler
    binder.bind(ScheduledJob.Builder.class).to(ImplScheduledJob.Builder.class);
    binder.bind(Scheduler.Builder.class).to(ImplScheduler.Builder.class);
    binder.bind(TimeCondition.CronBuilder.class).to(ImplTimeCondition.CronBuilder.class);
    binder.bind(UtsScheduler.class).to(ImplUtsScheduler.class);
    binder.bind(SyncScheduler.class).to(ImplSyncScheduler.class);
    // tasker
    binder.bind(Tasker.Factory.class).to(ImplTasker.Factory.class);
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
