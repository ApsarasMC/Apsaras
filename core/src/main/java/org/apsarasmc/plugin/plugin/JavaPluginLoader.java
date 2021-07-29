package org.apsarasmc.plugin.plugin;

import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class JavaPluginLoader extends URLClassLoader {
    private static final Collection<String> neverLoadCollection;
    static {
        Collection<String> neverLoadPrefixes = new ArrayList<>();
        neverLoadPrefixes.add("org.apsarasmc.plugin");
        neverLoadPrefixes.add("org.apsarasmc.sponge");
        neverLoadPrefixes.add("org.apsarasmc.spigot");
        neverLoadPrefixes.add("org.apsarasmc.loader");
        neverLoadCollection = Collections.unmodifiableCollection(neverLoadPrefixes);
    }

    private final Collection<ClassLoader> depends = new ArrayList<>();

    public JavaPluginLoader(final URL jarPath, final ClassLoader parent) {
        super(new URL[]{jarPath},parent);
    }

    @Override
    protected Class<?> loadClass(final String name, final  boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = super.findLoadedClass(name);
            if (c == null) {
                try {
                    return super.findClass(name);
                } catch (ClassNotFoundException ignore) {

                }
            }
            if (c == null) {
                for (ClassLoader depend : depends) {
                    try {
                        c = depend.loadClass(name);
                        break;
                    } catch (ClassNotFoundException ignore) {

                    }
                }
            }
            if (c == null) {
                try {
                    c = getParent().loadClass(name);
                } catch (ClassNotFoundException ignore) {

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
}
