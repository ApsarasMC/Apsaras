package org.apsarasmc.plugin.scheduler;

import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.scheduler.Task;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class CompletableFutureTask< T > implements Task< T > {
  private final PluginContainer plugin;
  private final CompletableFuture< T > completableFuture;

  public CompletableFutureTask(final @Nonnull PluginContainer plugin, final @Nonnull CompletableFuture< T > completableFuture) {
    this.plugin = plugin;
    this.completableFuture = completableFuture;
  }

  @Override
  public T get() {
    try {
      return completableFuture.get();
    } catch (ExecutionException e) {
      plugin.logger().warn("Failed to get task's result.", e.getCause());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } catch (Exception e) {
      plugin.logger().warn("Failed to get task's result.", e);
    }
    return null;
  }

  @Override
  public < R > Task< R > then(Function< T, R > command) {
    return new CompletableFutureTask<>(this.plugin, completableFuture.thenApply(command));
  }

  @Override
  public boolean isDone() {
    return completableFuture.isDone();
  }

  @Override
  public boolean hasException() {
    return completableFuture.isCompletedExceptionally();
  }

  @Override
  public T getWithException() throws Throwable {
    try {
      return completableFuture.get();
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }
}
