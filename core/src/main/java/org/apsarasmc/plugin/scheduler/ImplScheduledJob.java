package org.apsarasmc.plugin.scheduler;

import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.scheduler.ScheduledJob;
import org.apsarasmc.apsaras.scheduler.TimeCondition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ImplScheduledJob implements ScheduledJob {
  public final Runnable command;
  public final PluginContainer plugin;
  public final List<TimeCondition> timeConditions;

  public ImplScheduledJob(Runnable command, PluginContainer plugin, List< TimeCondition > timeConditions) {
    this.command = command;
    this.plugin = plugin;
    this.timeConditions = timeConditions;
  }

  @Override
  public Runnable command() {
    return command;
  }

  @Override
  public PluginContainer plugin() {
    return plugin;
  }

  @Override
  public Collection< TimeCondition > timeConditions() {
    return timeConditions;
  }

  public static class Builder implements ScheduledJob.Builder {
    public List<Runnable> runnableList = new ArrayList<>();
    public PluginContainer plugin;
    public List<TimeCondition> timeConditions = new ArrayList<>();
    @Override
    public ScheduledJob build() {
      if(timeConditions.size() == 0){
        throw new IllegalArgumentException("TimeConditions can't be null.");
      }
      if(runnableList.size() == 0){
        throw new IllegalArgumentException("No Runnable found.");
      }
      Objects.requireNonNull(plugin,"plugin");
      if(runnableList.size() == 1){
        return new ImplScheduledJob(runnableList.get(0), plugin, timeConditions);
      }else {
        return new ImplScheduledJob(()->{
          for (Runnable runnable : runnableList) {
            runnable.run();
          }
        },plugin,timeConditions);
      }
    }

    @Override
    public ScheduledJob.Builder command(Runnable command) {
      runnableList.add(command);
      return this;
    }

    @Override
    public ScheduledJob.Builder plugin(PluginContainer pluginContainer) {
      this.plugin = pluginContainer;
      return this;
    }

    @Override
    public ScheduledJob.Builder timeCondition(TimeCondition timeCondition) {
      timeConditions.add(timeCondition);
      return this;
    }
  }
}
