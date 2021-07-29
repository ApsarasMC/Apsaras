package org.apsarasmc.sponge.scheduler;

import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.scheduler.SchedulerService;
import org.apsarasmc.apsaras.scheduler.Task;
import org.apsarasmc.plugin.scheduler.CompletableFutureTask;
import org.apsarasmc.plugin.util.RunnableUtil;
import org.apsarasmc.sponge.SpongeCore;
import org.spongepowered.api.Sponge;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Singleton
public class SyncScheduler implements SchedulerService {
    @Inject
    private SpongeCore core;

    @Override
    public <T> Task<T> runLater(final PluginContainer plugin, final Callable<T> command, final int delay, final TimeUnit timeUnit) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        Sponge.server().scheduler().submit(
                org.spongepowered.api.scheduler.Task.builder()
                        .plugin(core.wrapper())
                        .delay(delay, timeUnit)
                        .execute(RunnableUtil.runnable(command, completableFuture))
                        .build()
        );
        return new CompletableFutureTask<>(plugin, completableFuture);
    }
}
