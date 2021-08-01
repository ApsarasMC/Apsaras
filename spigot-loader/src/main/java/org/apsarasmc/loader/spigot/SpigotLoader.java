package org.apsarasmc.loader.spigot;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SpigotLoader extends URLClassLoader {
  private static final Collection< String > parentPrefixCollection;

  static {
    Collection< String > parentPrefixes = new ArrayList<>();
    parentPrefixes.add("javax");
    parentPrefixes.add("net.kyori.examination");
    parentPrefixes.add("net.kyori.adventure");
    parentPrefixCollection = Collections.unmodifiableCollection(parentPrefixes);
  }

  public SpigotLoader(URL jarPath, ClassLoader parent) {
    super(new URL[] { jarPath }, parent);
  }

  @Override
  protected Class< ? > loadClass(String name, boolean resolve) throws ClassNotFoundException {
    synchronized (getClassLoadingLock(name)) {
      Class< ? > c = null;
      for (String parentPrefix : parentPrefixCollection) {
        if (name.startsWith(parentPrefix)) {
          c = super.getParent().loadClass(name);
        }
      }
      if (c == null) {
        c = super.findLoadedClass(name);
      }
      if (c == null) {
        try {
          c = super.findClass(name);
        } catch (ClassNotFoundException ignore) {
          //
        }
      }
      if (c == null) {
        try {
          c = super.getParent().loadClass(name);
        } catch (ClassNotFoundException ignore) {
          //
        }
      }
      if (c == null) {
        throw new ClassNotFoundException(name);
      }
      if (resolve) {
        resolveClass(c);
      }
      return c;
    }
  }

  @Override
  public URL getResource(String name) {
    URL u = super.findResource(name);
    if (u != null) {
      return u;
    }
    return super.getParent().getResource(name);
  }
}