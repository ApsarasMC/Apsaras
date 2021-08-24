package org.apsarasmc.plugin.scheduler;

import org.apsarasmc.apsaras.scheduler.TimeCondition;

import java.util.Objects;

public class ImplTimeCondition implements TimeCondition {
  private ImplTimeCondition() {
    //
  }

  public static class CronBuilder implements TimeCondition.CronBuilder {
    private String express;
    @Override
    public TimeCondition.Cron build() {
      return () -> express;
    }

    @Override
    public TimeCondition.CronBuilder cron(String express) {
      Objects.requireNonNull(express, "express");
      this.express = express;
      return this;
    }
  }
}
