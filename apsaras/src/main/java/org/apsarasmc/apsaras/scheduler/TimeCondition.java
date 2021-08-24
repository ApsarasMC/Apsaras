package org.apsarasmc.apsaras.scheduler;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.builder.AbstractBuilder;

public interface TimeCondition {
  static Cron cron(String express){
    return Apsaras.injector().getInstance(CronBuilder.class).cron(express).build();
  }

  interface Cron extends TimeCondition{
    String express();
  }

  interface CronBuilder extends AbstractBuilder<Cron> {
    CronBuilder cron(String express);
  }
}
