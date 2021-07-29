package org.apsarasmc.apsaras.scheduler;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.plugin.PluginContainer;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public interface SchedulerService {
    static Factory factory() {
        return Apsaras.injector().getInstance(Factory.class);
    }

    <T> Task<T> runLater(PluginContainer plugin, Callable<T> command, int delay, TimeUnit timeUnit);

    default Task<Void> runLater(PluginContainer plugin, Runnable command, int delay, TimeUnit timeUnit) {
        return this.runLater(plugin, () -> {
            command.run();
            return null;
        }, delay, timeUnit);
    }

    default <T> Task<T> run(PluginContainer plugin, Callable<T> command) {
        return this.runLater(plugin, command, 0);
    }

    default Task<Void> run(PluginContainer plugin, Runnable command) {
        return this.run(plugin, () -> {
            command.run();
            return null;
        });
    }

    default <T> Task<T> runLater(PluginContainer plugin, Callable<T> command, int delay) {
        return runLater(plugin, command, delay, TimeUnit.MILLISECONDS);
    }

    default Task<Void> runLater(PluginContainer plugin, Runnable command, int delay) {
        return runLater(plugin, () -> {
            command.run();
            return null;
        }, delay, TimeUnit.MILLISECONDS);
    }

    default void shutdown() {
        throw new UnsupportedOperationException("Current scheduler service don't support shutdown.");
    }


    interface Factory {
        SchedulerService of(PluginContainer plugin, int threads, String name);

        default SchedulerService of(PluginContainer plugin, String name) {
            return this.of(plugin, name);
        }

        default SchedulerService of(PluginContainer plugin) {
            return this.of(plugin, plugin.name() + "-worker");
        }

        Collection<SchedulerService> all(PluginContainer plugin);

        void unregister(PluginContainer plugin);
    }
}