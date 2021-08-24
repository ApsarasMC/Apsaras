package org.apsarasmc.plugin.scheduler;

import org.quartz.SchedulerConfigException;
import org.quartz.spi.ThreadPool;

import java.util.function.Consumer;

public class FakeSchedulerThreadPool implements ThreadPool {
  private final Consumer<Runnable> taskPoster;
  private final int threads;
  public FakeSchedulerThreadPool(Consumer< Runnable > taskPoster, int threads) {

    this.taskPoster = taskPoster;
    this.threads = threads;
  }

  @Override
  public boolean runInThread(Runnable runnable) {
    taskPoster.accept(runnable);
    return true;
  }

  @Override
  public int blockForAvailableThreads() {
    return threads;
  }

  @Override
  public void initialize() throws SchedulerConfigException {
    //
  }

  @Override
  public void shutdown(boolean waitForJobsToComplete) {
    //
  }

  @Override
  public int getPoolSize() {
    return threads;
  }

  @Override
  public void setInstanceId(String schedInstId) {
    //
  }

  @Override
  public void setInstanceName(String schedName) {
    //
  }
}
