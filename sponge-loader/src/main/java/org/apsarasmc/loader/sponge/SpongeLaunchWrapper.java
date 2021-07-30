package org.apsarasmc.loader.sponge;

import com.google.inject.Inject;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.jvm.Plugin;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Plugin ("apsaras")
public class SpongeLaunchWrapper extends URLClassLoader {
  private static final Collection< String > parentPrefixCollection;

  static {
    Collection< String > parentPrefixes = new ArrayList<>();
    parentPrefixes.add("org.apsarasmc.apsaras");
    parentPrefixes.add("javax");
    parentPrefixes.add("net.kyori.examination");
    parentPrefixes.add("net.kyori.adventure");
    parentPrefixCollection = Collections.unmodifiableCollection(parentPrefixes);
  }

  @Inject
  public SpongeLaunchWrapper(final PluginContainer pluginContainer) {
    super(new URL[ 0 ], SpongeLaunchWrapper.class.getClassLoader());
    try {
      String s = SpongeLaunchWrapper.class.getProtectionDomain().getCodeSource().getLocation().toString();
      addURL(new URL(s.substring(0, s.indexOf('!') + 2)));

      Class< ? > clazz = loadClass("org.apsarasmc.sponge.SpongeCore");
      clazz.getMethod("init").invoke(
        clazz.getConstructor(PluginContainer.class).newInstance(pluginContainer)
      );
    } catch (Exception exception) {
      exception.printStackTrace();
    }
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