package org.apsarasmc.sponge.legacy.tasker;

import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.tasker.Task;
import org.apsarasmc.plugin.tasker.CompletableFutureTask;
import org.apsarasmc.plugin.util.RunnableUtil;
import org.apsarasmc.sponge.legacy.SpongeCore;
import org.spongepowered.api.Sponge;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Singleton
public class SpongeUtsTasker implements org.apsarasmc.apsaras.tasker.UtsTasker {
  @Inject
  private SpongeCore core;

  @Override
  public < T > Task< T > runLater(final PluginContainer plugin, final Callable< T > command, final int delay, final TimeUnit timeUnit) {
    CompletableFuture< T > completableFuture = new CompletableFuture<>();
    org.spongepowered.api.scheduler.Task.Builder builder = Sponge.getScheduler().createTaskBuilder();
    builder = builder.async();
    if(delay != 0){
      builder = builder.delay(delay, timeUnit);
    }
    builder = builder.execute(RunnableUtil.runnable(command, completableFuture));
    builder.submit(core.wrapperInstance());
    return new CompletableFutureTask<>(plugin, completableFuture);
  }
}
