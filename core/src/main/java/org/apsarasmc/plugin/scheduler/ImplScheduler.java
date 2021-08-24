package org.apsarasmc.plugin.scheduler;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.scheduler.JobToken;
import org.apsarasmc.apsaras.scheduler.ScheduledJob;
import org.apsarasmc.apsaras.scheduler.Scheduler;
import org.apsarasmc.apsaras.scheduler.TimeCondition;
import org.apsarasmc.apsaras.tasker.Tasker;
import org.quartz.*;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.simpl.RAMJobStore;

import java.util.*;

public class ImplScheduler implements Scheduler {
  private final org.quartz.Scheduler scheduler;
  private int n = 0;
  protected ImplScheduler(PluginContainer plugin, Tasker tasker, String name) throws SchedulerException {
    DirectSchedulerFactory.getInstance().createScheduler(
      plugin.name()+"-"+name,
      "scheduler",
      new FakeSchedulerThreadPool(runnable -> tasker.run(plugin, runnable), 1),
      new RAMJobStore());
    scheduler = DirectSchedulerFactory.getInstance().getScheduler(plugin.name()+"-"+name);
    scheduler.start();
  }

  @Override
  public JobToken commit(ScheduledJob job) {
    Set<Trigger> triggers = new HashSet<>();
    int i = 0;
    for (TimeCondition timeCondition : job.timeConditions()) {
      if(timeCondition instanceof TimeCondition.Cron){
        triggers.add(TriggerBuilder.newTrigger()
          .withIdentity(n + "-" + (i++), job.plugin().name())
          .withSchedule(
            CronScheduleBuilder.cronSchedule(
              ((TimeCondition.Cron) timeCondition).express())
          ).build()
        );
      }
    }

    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put("true-task",job.command());
    JobDetail jobDetail = JobBuilder.newJob(ImplJob.class)
      .withIdentity(String.valueOf(n), job.plugin().name())
      .setJobData(jobDataMap).build();
    n++;
    try {
      scheduler.scheduleJob(jobDetail, triggers, false);
    } catch (SchedulerException e) {
      Apsaras.server().logger().warn("Failed to commit schedule job.", e);
    }
    return null;
  }

  @Override
  public void cancel(JobToken job) {
    if(!(job instanceof ImplJobToken)) {
      return;
    }
    try {
      scheduler.deleteJob(((ImplJobToken) job).jobDetail().getKey());
    } catch (SchedulerException e) {
      Apsaras.server().logger().warn("Failed to delete schedule job.", e);
    }
    for (Trigger trigger : ((ImplJobToken) job).triggers()) {
      try {
        scheduler.pauseTrigger(trigger.getKey());
      } catch (SchedulerException e) {
        Apsaras.server().logger().warn("Failed to delete schedule job trigger.", e);
      }
    }
  }

  public static class Builder implements Scheduler.Builder {
    private PluginContainer plugin;
    private Tasker tasker;
    private String name;
    @Override
    public Scheduler build() {
      try {
        return new ImplScheduler(plugin, tasker, name);
      }catch (SchedulerException e){
        throw new IllegalStateException("Failed to instance Scheduler.", e);
      }
    }

    @Override
    public Scheduler.Builder plugin(PluginContainer plugin) {
      this.plugin = plugin;
      return this;
    }

    @Override
    public Scheduler.Builder taskerService(Tasker tasker) {
      this.tasker = tasker;
      return this;
    }

    @Override
    public Scheduler.Builder name(String name) {
      this.name = name;
      return this;
    }
  }
}
