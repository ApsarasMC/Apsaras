package org.apsarasmc.plugin.util.relocate;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

public class ClassRemapperVisitor extends ClassRemapper {
  public ClassRemapperVisitor(ClassVisitor classVisitor, Remapper remapper) {
    super(classVisitor, remapper);
  }

  @Override
  protected MethodVisitor createMethodRemapper(MethodVisitor methodVisitor) {
    return new MethodRemapperVisitor(methodVisitor, remapper);
  }
}
