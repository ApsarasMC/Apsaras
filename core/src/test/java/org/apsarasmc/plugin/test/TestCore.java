package org.apsarasmc.plugin.test;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.scheduler.SyncScheduler;
import org.apsarasmc.apsaras.scheduler.UtsScheduler;
import org.apsarasmc.apsaras.tasker.SyncTasker;
import org.apsarasmc.apsaras.tasker.UtsTasker;
import org.apsarasmc.plugin.ImplGame;
import org.apsarasmc.plugin.ImplServer;
import org.apsarasmc.plugin.test.event.Handlers;
import org.apsarasmc.plugin.test.tasker.TestSyncTasker;
import org.apsarasmc.plugin.test.tasker.TestUtsTasker;
import org.apsarasmc.plugin.util.relocate.RelocatingRemapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;


public class TestCore implements ImplServer {
  @Inject
  private Handlers handlers;
  @Inject
  private SyncTasker syncTasker;
  @Inject
  private UtsTasker utsTasker;
  @Inject
  private SyncScheduler syncScheduler;
  @Inject
  private UtsScheduler utsScheduler;

  public void init() {
    new ImplGame(new TestModule(binder -> {
      binder.bind(ImplServer.class).toInstance(this);
      binder.bind(TestCore.class).toInstance(this);
    }));
    Apsaras.injector().injectMembers(this);

    try {
      Path pluginsPath = this.pluginPath().resolve("plugins");
      Files.createDirectories(pluginsPath);
      Arrays.stream(
        Objects.requireNonNull(pluginsPath.toFile().listFiles())
      ).filter(file -> file.getName().endsWith(".jar")).forEach(file -> {
        Apsaras.pluginManager().addPlugin(file);
      });
    } catch (Exception e) {
      this.logger().warn("Failed to open plugins dir.", e);
    }
    Apsaras.pluginManager().load();
    Apsaras.pluginManager().enable();

    this.handlers.register();
  }

  @Override
  public SyncTasker syncTasker() {
    return syncTasker;
  }

  @Override
  public UtsTasker utsTasker() {
    return utsTasker;
  }

  @Override
  public SyncScheduler syncScheduler() {
    return syncScheduler;
  }

  @Override
  public UtsScheduler utsScheduler() {
    return utsScheduler;
  }

  @Override
  public Logger logger() {
    return LoggerFactory.getLogger("apsaras");
  }

  @Override
  public ClassLoader classLoader() {
    return TestCore.class.getClassLoader();
  }

  @Override
  public ClassLoader apiClassLoader() {
    return classLoader();
  }

  @Override
  public Path gamePath() {
    return new File("./run").toPath();
  }

  @Override
  public Path pluginPath() {
    return gamePath().resolve("plugins");
  }

  @Override
  public String version() {
    return "test";
  }

  @Override
  public void dispatchCommand(String command) {
    //
  }

  @Override
  public RelocatingRemapper remapper() {
    return new RelocatingRemapper(Collections.emptyList());
  }
}
