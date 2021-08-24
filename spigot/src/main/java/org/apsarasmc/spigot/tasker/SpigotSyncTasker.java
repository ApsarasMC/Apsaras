package org.apsarasmc.spigot.tasker;

import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.tasker.Task;
import org.apsarasmc.plugin.tasker.CompletableFutureTask;
import org.apsarasmc.plugin.util.RunnableUtil;
import org.apsarasmc.spigot.SpigotCore;
import org.bukkit.Bukkit;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Singleton
public class SpigotSyncTasker implements org.apsarasmc.apsaras.tasker.SyncTasker {
  @Inject
  private SpigotCore core;

  @Override
  public < T > Task< T > runLater(PluginContainer plugin, Callable< T > command, int delay, TimeUnit timeUnit) {
    CompletableFuture< T > completableFuture = new CompletableFuture<>();
    Bukkit.getScheduler().runTaskLater(core.wrapper(), RunnableUtil.runnable(command, completableFuture), timeUnit.toMillis(delay) / 50);
    return new CompletableFutureTask<>(plugin, completableFuture);
  }
}
