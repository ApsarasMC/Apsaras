package org.apsarasmc.plugin.test.scheduler;

import org.apsarasmc.apsaras.scheduler.SchedulerService;
import org.apsarasmc.plugin.ApsarasPluginContainer;
import org.apsarasmc.plugin.test.InjectTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import javax.inject.Inject;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@InjectTest
class SchedulerTest {
  @Inject
  private ApsarasPluginContainer pluginContainer;

  @Test
  @Timeout (value = 400, unit = TimeUnit.MILLISECONDS)
  void schedule() throws InterruptedException {
    SchedulerService schedulerService = SchedulerService.factory().of(pluginContainer, 2, "schedule-test");
    CountDownLatch countDownLatch = new CountDownLatch(6);
    for (int i = 0; i < 6; i++) {
      schedulerService.run(pluginContainer, countDownLatch::countDown);
    }
    Assertions.assertNotEquals(6, countDownLatch.getCount());
    countDownLatch.await();
  }
}
