package org.apsarasmc.plugin.util.relocate;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.Remapper;

import java.io.IOException;
import java.io.InputStream;

public class AsmRelocate {
  private AsmRelocate() {
    //
  }

  public static byte[] apply(InputStream entryIn, Remapper remapper) throws IOException {
    ClassReader classReader = new ClassReader(entryIn);
    ClassWriter classWriter = new ClassWriter(0);
    ClassVisitor classVisitor = new ClassRemapperVisitor(classWriter, remapper);
    classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
    return classWriter.toByteArray();
  }
}
