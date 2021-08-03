package org.apsarasmc.plugin.util.relocate;

import org.objectweb.asm.commons.Remapper;

import java.util.Collection;

public class RelocatingRemapper extends Remapper {
  private final Collection< Relocation > rules;

  public RelocatingRemapper(Collection< Relocation > rules) {
    this.rules = rules;
  }

  @Override
  public String map(final String name) {
    String result = name;
    for (Relocation r : this.rules) {
      if (r.canRelocatePath(name)) {
        result = r.relocatePath(name);
        break;
      }
    }
    return result;
  }

  public String mapClass(String name) {
    for (Relocation r : this.rules) {
      if (r.canRelocateClass(name)) {
        return r.relocateClass(name);
      }
    }
    return name;
  }


  public String unMap(String name) {
    for (Relocation r : this.rules) {
      if (r.canUnRelocatePath(name)) {
        return r.unRelocatePath(name);
      }
    }
    return name;
  }

  public String unMapClass(String name) {
    for (Relocation r : this.rules) {
      if (r.canUnRelocateClass(name)) {
        return r.unRelocateClass(name);
      }
    }
    return name;
  }
}