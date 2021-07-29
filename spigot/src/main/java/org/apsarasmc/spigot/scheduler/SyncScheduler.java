package org.apsarasmc.spigot.scheduler;

import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.scheduler.SchedulerService;
import org.apsarasmc.apsaras.scheduler.Task;
import org.apsarasmc.plugin.scheduler.CompletableFutureTask;
import org.apsarasmc.plugin.util.RunnableUtil;
import org.apsarasmc.spigot.SpigotCore;
import org.bukkit.Bukkit;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Singleton
public class SyncScheduler implements SchedulerService {
    @Inject
    private SpigotCore core;

    @Override
    public <T> Task<T> runLater(PluginContainer plugin, Callable<T> command, int delay, TimeUnit timeUnit) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskLater(core.wrapper(), RunnableUtil.runnable(command, completableFuture), timeUnit.toMillis(delay) / 50);
        return new CompletableFutureTask<>(plugin, completableFuture);
    }
}
