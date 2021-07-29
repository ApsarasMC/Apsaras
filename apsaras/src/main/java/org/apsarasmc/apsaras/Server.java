package org.apsarasmc.apsaras;

import org.apsarasmc.apsaras.scheduler.SchedulerService;
import org.slf4j.Logger;

import java.nio.file.Path;

public interface Server {
    SchedulerService sync();

    SchedulerService uts();

    Logger logger();

    ClassLoader classLoader();

    Path gamePath();

    Path pluginPath();

    String version();
}