package org.apsarasmc.plugin.util.relocate;

public final class Relocation {
  private final String pattern;
  private final String relocatedPattern;
  private final String pathPattern;
  private final String relocatedPathPattern;


  public Relocation(String pattern, String relocatedPattern) {
    this.pattern = pattern.replace("{}", ".").replace('/', '.');
    this.pathPattern = pattern.replace("{}", "/").replace('.', '/');
    this.relocatedPattern = relocatedPattern.replace("{}", ".").replace('/', '.');
    this.relocatedPathPattern = relocatedPattern.replace("{}", "/").replace('.', '/');
  }

  boolean canRelocatePath(String path) {
    if (path.endsWith(".class")) {
      path = path.substring(0, path.length() - 6);
    }

    return path.startsWith(this.pathPattern) || path.startsWith("/" + this.pathPattern);
  }

  String relocatePath(String path) {
    return path.replaceFirst(this.pathPattern, this.relocatedPathPattern);
  }

  boolean canRelocateClass(String name) {
    return name.indexOf('/') == -1 && name.startsWith(this.pattern);
  }

  String relocateClass(String path) {
    return path.replaceFirst(this.pattern, this.relocatedPattern);
  }

  boolean canUnRelocatePath(String path) {
    if (path.endsWith(".class")) {
      path = path.substring(0, path.length() - 6);
    }

    return path.startsWith(this.relocatedPathPattern) || path.startsWith("/" + this.relocatedPathPattern);
  }

  String unRelocatePath(String path) {
    return path.replaceFirst(this.relocatedPathPattern, this.pathPattern);
  }

  boolean canUnRelocateClass(String name) {
    return name.indexOf('/') == -1 && name.startsWith(this.relocatedPattern);
  }

  String unRelocateClass(String name) {
    return name.replaceFirst(this.relocatedPattern, this.pattern);
  }
}
