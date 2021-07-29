package org.apsarasmc.plugin.test.scheduler;

import org.apsarasmc.apsaras.scheduler.SchedulerService;
import org.apsarasmc.plugin.ApsarasPluginContainer;
import org.apsarasmc.plugin.test.InjectTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import javax.inject.Inject;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@InjectTest
public class SchedulerTest {
    @Inject
    private ApsarasPluginContainer pluginContainer;

    @Test
    @Timeout(value = 400, unit = TimeUnit.MILLISECONDS)
    public void schedule() throws InterruptedException {
        SchedulerService schedulerService = SchedulerService.factory().of(pluginContainer, 3, "schedule-test");
        CountDownLatch countDownLatch = new CountDownLatch(2);
        for (int i = 0; i < 6; i++) {
            schedulerService.run(pluginContainer, () -> {
                try {
                    Thread.sleep(100);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        countDownLatch.await();
    }
}
