package org.apsarasmc.plugin.util;

import com.google.inject.Injector;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.plugin.plugin.JavaPluginContainer;
import org.apsarasmc.plugin.util.relocate.PluginContainerEntry;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class StaticEntryUtil {
  public static final String APSARAS_ENTRY_CLASS = Apsaras.class.getName();
  public static final String REMAP_ENTRY_CLASS = PluginContainerEntry.class.getName();
  private static final int BUFFER_SIZE = 4096;

  private StaticEntryUtil() {
    //
  }

  public static void applyInjector(ClassLoader classLoader, Injector injector) {
    try {
      Class< ? > clazz = classLoader.loadClass(APSARAS_ENTRY_CLASS);
      Field gameField = clazz.getDeclaredField("injector");
      // For StaticEntry
      gameField.setAccessible(true);
      gameField.set(null, injector);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void applyPluginContainer(ClassLoader classLoader, JavaPluginContainer pluginContainer) {
    try {
      Class< ? > clazz = classLoader.loadClass(REMAP_ENTRY_CLASS);
      Field gameField = clazz.getDeclaredField("container");
      // For StaticEntry
      gameField.setAccessible(true);
      gameField.set(null, pluginContainer);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static boolean isStaticEntry(String name) {
    return APSARAS_ENTRY_CLASS.equals(name)
      || REMAP_ENTRY_CLASS.equals(name);
  }

  public static byte[] getEntryBuffer(String name) throws ClassNotFoundException {
    String internalName = name.replace('.', '/') + ".class";
    InputStream inputStream = Apsaras.class.getClassLoader().getResourceAsStream(internalName);
    if (inputStream == null) {
      throw new ClassNotFoundException(name);
    }

    try {
      try {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
          byte[] buffer = new byte[ BUFFER_SIZE ];
          int bytesRead = -1;
          while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
          }
          inputStream.close();
          return outputStream.toByteArray();
        }
      } finally {
        inputStream.close();
      }
    } catch (IOException ex) {
      throw new ClassNotFoundException("Cannot load resource for class [" + name + "]", ex);
    }
  }

  public static JavaPluginContainer getPluginContainer(ClassLoader classLoader) {
    try {
      return (JavaPluginContainer) classLoader.loadClass(REMAP_ENTRY_CLASS).getMethod("container").invoke(null);
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      return null;
    }
  }

  public static Injector getInjector(ClassLoader classLoader) {
    try {
      return (Injector) classLoader.loadClass(APSARAS_ENTRY_CLASS).getMethod("injector").invoke(null);
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      return null;
    }
  }
}
