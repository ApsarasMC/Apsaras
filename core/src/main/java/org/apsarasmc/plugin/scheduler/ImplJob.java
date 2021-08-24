package org.apsarasmc.plugin.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ImplJob implements Job {
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    ((Runnable)context.getMergedJobDataMap().get("true-task")).run();
  }
}
