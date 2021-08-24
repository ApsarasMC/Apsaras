package org.apsarasmc.plugin.test.scheduler;

import com.google.inject.Inject;
import org.apsarasmc.apsaras.scheduler.ScheduledJob;
import org.apsarasmc.apsaras.scheduler.SyncScheduler;
import org.apsarasmc.apsaras.scheduler.TimeCondition;
import org.apsarasmc.plugin.ApsarasPluginContainer;
import org.apsarasmc.plugin.test.InjectTest;
import org.junit.jupiter.api.Test;

@InjectTest
class SchedulerTest {
  @Inject
  private SyncScheduler syncScheduler;
  @Inject
  private ApsarasPluginContainer plugin;
  @Test
  void test() throws InterruptedException {
    syncScheduler.commit(
      ScheduledJob.builder()
        .plugin(plugin)
        .command(()->{
          plugin.logger().info("runed");
        })
        .timeCondition(TimeCondition.cron("0/3 * * * * ?"))
        .build()
    );
  }
}
