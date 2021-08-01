package org.apsarasmc.plugin.util.relocate;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.MethodRemapper;
import org.objectweb.asm.commons.Remapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MethodRemapperVisitor extends MethodRemapper {
  public static final Map<MethodRelocate, MethodRelocate> methodRelocateMap;
  static {
    Map<MethodRelocate, MethodRelocate> methodRelocateMap1 = new HashMap<>();
    methodRelocateMap1.put(new MethodRelocate(Opcodes.INVOKESTATIC, "java/lang/Class","forName","(Ljava/lang/String;)Ljava/lang/Class;"),
      new MethodRelocate(Opcodes.INVOKESTATIC, "org/apsarasmc/plugin/util/relocate/PluginContainerEntry", "forName", "(Ljava/lang/String;)Ljava/lang/Class;"));
    methodRelocateMap1.put(new MethodRelocate(Opcodes.INVOKESTATIC, "java/lang/Class","forName","(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;"),
      new MethodRelocate(Opcodes.INVOKESTATIC, "org/apsarasmc/plugin/util/relocate/PluginContainerEntry", "forName", "(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;"));
    methodRelocateMap1.put(new MethodRelocate(Opcodes.INVOKEVIRTUAL, "java/lang/Class","getName","()Ljava/lang/String;"),
      new MethodRelocate(Opcodes.INVOKESTATIC, "org/apsarasmc/plugin/util/relocate/PluginContainerEntry", "getName", "(Ljava/lang/Class;)Ljava/lang/String;"));
    methodRelocateMap1.put(new MethodRelocate(Opcodes.INVOKEVIRTUAL, "java/lang/Class","getSimpleName","()Ljava/lang/String;"),
      new MethodRelocate(Opcodes.INVOKESTATIC, "org/apsarasmc/plugin/util/relocate/PluginContainerEntry", "getSimpleName", "(Ljava/lang/Class;)Ljava/lang/String;"));
    methodRelocateMap1.put(new MethodRelocate(Opcodes.INVOKEVIRTUAL, "java/lang/Thread","getContextClassLoader","()Ljava/lang/ClassLoader;"),
      new MethodRelocate(Opcodes.INVOKESTATIC, "org/apsarasmc/plugin/util/relocate/PluginContainerEntry", "getContextClassLoader", "(Ljava/lang/Thread;)Ljava/lang/ClassLoader;"));
    methodRelocateMap = Collections.unmodifiableMap(methodRelocateMap1);
  }
  public MethodRemapperVisitor(MethodVisitor methodVisitor, Remapper remapper) {
    super(methodVisitor, remapper);
  }

  @Override
  public void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
    MethodRelocate input = new MethodRelocate(opcodeAndSource,owner,name,descriptor);
    MethodRelocate output = methodRelocateMap.get(input);
    output = output == null ? input : output;
    if (mv != null) {
      mv.visitMethodInsn(
        output.opcodeAndSource,
        remapper.mapType(output.owner),
        remapper.mapMethodName(output.owner, output.name, output.descriptor),
        remapper.mapMethodDesc(output.descriptor),
        isInterface);
    }
  }

  private static class MethodRelocate {
    public final int opcodeAndSource;
    public final String owner;
    public final String name;
    public final String descriptor;

    public MethodRelocate(final int opcodeAndSource, final String owner, final String name, final String descriptor) {
      this.opcodeAndSource = opcodeAndSource;
      this.owner = owner;
      this.name = name;
      this.descriptor = descriptor;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      MethodRelocate that = (MethodRelocate) o;
      return opcodeAndSource == that.opcodeAndSource && Objects.equals(owner, that.owner) && Objects.equals(name, that.name) && Objects.equals(descriptor, that.descriptor);
    }

    @Override
    public int hashCode() {
      return Objects.hash(opcodeAndSource, owner, name, descriptor);
    }
  }
}
