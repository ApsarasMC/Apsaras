package org.apsarasmc.plugin.scheduler;

import org.apsarasmc.apsaras.scheduler.JobToken;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import java.util.Set;

public class ImplJobToken implements JobToken {
  private final Set< Trigger> triggers;
  private final JobDetail jobDetail;
  public ImplJobToken(Set< Trigger> triggers, JobDetail jobDetail) {
    this.triggers = triggers;
    this.jobDetail = jobDetail;
  }

  public Set< Trigger > triggers() {
    return triggers;
  }

  public JobDetail jobDetail() {
    return jobDetail;
  }
}
