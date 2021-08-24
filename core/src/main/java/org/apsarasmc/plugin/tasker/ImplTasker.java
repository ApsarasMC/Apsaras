package org.apsarasmc.plugin.tasker;

import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.tasker.Tasker;
import org.apsarasmc.apsaras.tasker.Task;
import org.apsarasmc.plugin.util.RunnableUtil;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ImplTasker implements Tasker {
  private final AtomicInteger id = new AtomicInteger(0);
  private final ScheduledThreadPoolExecutor executor;

  protected ImplTasker(final @Nonnull PluginContainer plugin, final int threads, final @Nonnull String name) {
    this.executor = new ScheduledThreadPoolExecutor(threads, r -> new Thread(r, plugin.name() + "-" + name + id.getAndIncrement()));
  }

  @Override
  public < T > Task< T > runLater(PluginContainer plugin, Callable< T > command, int delay, TimeUnit timeUnit) {
    CompletableFuture< T > future = new CompletableFuture<>();
    this.executor.schedule(RunnableUtil.runnable(command, future), delay, timeUnit);
    return new CompletableFutureTask<>(plugin, future);
  }

  @Override
  public void shutdown() {
    Tasker.super.shutdown();
  }

  @Singleton
  public static class Factory implements Tasker.Factory {
    private final Map< PluginContainer, Collection< Tasker > > services = new HashMap<>();

    @Override
    public Tasker of(PluginContainer plugin, int threads, String name) {
      ImplTasker service = new ImplTasker(plugin, threads, name);
      Collection< Tasker > collection = services.computeIfAbsent(plugin, k -> new ArrayList<>());
      collection.add(service);
      return service;
    }

    @Override
    public Collection< Tasker > all(PluginContainer plugin) {
      return new ArrayList<>(services.get(plugin));
    }

    @Override
    public void unregister(PluginContainer plugin) {
      Collection< Tasker > collection = services.get(plugin);
      if (collection == null) {
        return;
      }
      for (Tasker schedulerService : collection) {
        schedulerService.shutdown();
      }
      services.remove(plugin);
    }
  }
}
