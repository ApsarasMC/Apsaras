package org.apsarasmc.apsaras;

import org.apsarasmc.apsaras.scheduler.SyncScheduler;
import org.apsarasmc.apsaras.scheduler.UtsScheduler;
import org.apsarasmc.apsaras.tasker.SyncTasker;
import org.apsarasmc.apsaras.tasker.UtsTasker;
import org.slf4j.Logger;

import java.nio.file.Path;

public interface Server {
  SyncTasker syncTasker();

  UtsTasker utsTasker();

  SyncScheduler syncScheduler();

  UtsScheduler utsScheduler();

  Logger logger();

  ClassLoader classLoader();

  ClassLoader apiClassLoader();

  Path gamePath();

  Path pluginPath();

  String version();

  void dispatchCommand(String command);
}