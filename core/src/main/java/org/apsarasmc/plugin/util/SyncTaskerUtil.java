package org.apsarasmc.plugin.util;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.event.EventListener;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.tasker.SyncTasker;

import java.util.concurrent.Callable;

public class SyncTaskerUtil {
  private SyncTaskerUtil() {
    //
  }

  private static final SyncTasker tasker = Apsaras.server().syncTasker();
  private static final PluginContainer plugin = Apsaras.game().self();

  public static Object sync(Callable<Object> command) throws Exception {
    if (tasker.isSyncThread()) {
      return command.call();
    }else {
      return tasker.run(plugin, command).getWithException();
    }
  }

  public static <T> EventListener<T> sync(EventListener<T> listener) {
    return event -> sync(() -> {
      listener.handle(event);
      return null;
    });
  }
}
