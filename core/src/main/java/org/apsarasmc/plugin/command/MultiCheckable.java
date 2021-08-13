package org.apsarasmc.plugin.command;

import org.apsarasmc.apsaras.command.Checkable;
import org.apsarasmc.apsaras.command.CommandContext;
import org.apsarasmc.apsaras.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MultiCheckable implements Checkable {
  private final List<Checkable> checkableList;

  public MultiCheckable(List< Checkable > checkableList) {
    this.checkableList = checkableList;
  }

  @Override
  public boolean check(CommandSender sender) {
    for (Checkable checkable : checkableList) {
      if(! checkable.check(sender)){
        return false;
      }
    }
    return true;
  }

  public static class Builder implements Checkable.Builder {
    private final List<Checkable> checkableList = new ArrayList<>();
    @Override
    public Checkable build() {
      if(checkableList.size() == 0){
        return Checkable.TRUE;
      }
      if(checkableList.size() == 1){
        return checkableList.get(0);
      }
      return new MultiCheckable(checkableList);
    }

    @Override
    public Checkable.Builder add(Checkable checker) {
      Objects.requireNonNull(checker, "checker");
      checkableList.add(checker);
      return this;
    }
  }
}
