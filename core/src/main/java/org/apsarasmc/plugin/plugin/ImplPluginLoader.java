package org.apsarasmc.plugin.plugin;

import org.apsarasmc.plugin.util.StaticEntryUtil;
import org.apsarasmc.plugin.util.relocate.AsmRelocate;
import org.apsarasmc.plugin.util.relocate.RelocatingRemapper;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collection;

public class ImplPluginLoader extends URLClassLoader {
  private final Collection< String > apiPrefixes = new ArrayList<>();

  private final Collection< ClassLoader > depends = new ArrayList<>();
  private final RelocatingRemapper remapper;
  private final ClassLoader api;

  public ImplPluginLoader(final URL[] urls, final ClassLoader parent, final ClassLoader api, RelocatingRemapper remapper) {
    super(urls, parent);
    this.api = api;
    this.remapper = remapper;
  }

  public RelocatingRemapper remapper() {
    return remapper;
  }

  @Override
  protected Class< ? > loadClass(final String unRelocatedName, final boolean resolve) throws ClassNotFoundException {
    String name = remapper.mapClass(unRelocatedName);
    synchronized (getClassLoadingLock(name)) {
      Class< ? > c = super.findLoadedClass(name);
      if (c == null) {
        c = rewriteStaticEntry(name);
      }
      if (c == null) {
        try {
          c = findClass(name);
        } catch (ClassNotFoundException ignore) {
          //
        }
      }
      if (c == null) {
        c = tryDepends(name);
      }
      if (c == null) {
        try {
          c = getParent().loadClass(name);
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

  private Class< ? > rewriteStaticEntry(final String name) throws ClassNotFoundException {
    if (StaticEntryUtil.isStaticEntry(name)) {
      byte[] bytes = StaticEntryUtil.getEntryBuffer(name);
      return defineClass(name, bytes, 0, bytes.length);
    }
    return null;
  }

  private Class< ? > tryDepends(final String name) {
    for (ClassLoader depend : depends) {
      try {
        return depend.loadClass(name);
      } catch (ClassNotFoundException ignore) {
        //
      }
    }
    return null;
  }

  @Override
  protected Class< ? > findClass(String name) throws ClassNotFoundException {
    String path = this.remapper.unMapClass(name).replace('.', '/').concat(".class");
    URL classUrl = findResource(path);
    if (classUrl != null) {
      try {
        byte[] bytes = AsmRelocate.apply(classUrl.openStream(), this.remapper);
        return defineClass(name, bytes, 0, bytes.length, (ProtectionDomain) null);
      } catch (IOException e) {
        throw new ClassNotFoundException(name, e);
      }
    } else {
      throw new ClassNotFoundException(name);
    }
  }

  @Nullable
  @Override
  public URL getResource(final String name) {
    URL u = super.findResource(name);
    if (u != null) {
      return u;
    }
    for (ClassLoader depend : depends) {
      u = depend.getResource(name);
      if (u != null) {
        return u;
      }
    }
    return getParent().getResource(name);
  }

  public void addDepend(final ClassLoader depend) {
    this.depends.add(depend);
  }

  @Override
  public void addURL(final URL url) {
    super.addURL(url);
  }

  public void addApi(String api) {
    this.apiPrefixes.add(api);
  }
}
