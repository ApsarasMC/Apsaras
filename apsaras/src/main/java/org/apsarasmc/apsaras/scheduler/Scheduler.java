package org.apsarasmc.apsaras.scheduler;

import org.apsarasmc.apsaras.plugin.PluginContainer;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface Scheduler {
    SchedulerService sync();

    SchedulerService uts();

    default Collection<SchedulerService> all(PluginContainer pluginContainer) {
        return SchedulerService.factory().all(pluginContainer);
    }

    default SchedulerService startup(final @Nonnull PluginContainer plugin, final int threads, final @Nonnull String name) {
        return SchedulerService.factory().of(plugin, threads, name);
    }
}
