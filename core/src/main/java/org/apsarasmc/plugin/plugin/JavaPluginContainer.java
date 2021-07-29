package org.apsarasmc.plugin.plugin;

import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.Game;
import org.apsarasmc.apsaras.Server;
import org.apsarasmc.apsaras.event.EventHandler;
import org.apsarasmc.apsaras.event.EventListener;
import org.apsarasmc.apsaras.event.EventManager;
import org.apsarasmc.apsaras.event.lifecycle.PluginLifeEvent;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.plugin.PluginDepend;
import org.apsarasmc.apsaras.plugin.PluginMeta;
import org.apsarasmc.plugin.dependency.DependencyResolver;
import org.apsarasmc.plugin.event.lifecycle.ImplPluginLifeEvent;
import org.apsarasmc.plugin.util.ImplInjector;
import org.apsarasmc.plugin.util.ImplPrefixLogger;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;

public class JavaPluginContainer implements PluginContainer {
    private final File jarFile;
    @Inject
    private DependencyResolver dependencyResolver;
    @Inject
    private EventManager eventManager;
    @Inject
    private Server server;
    private boolean enable = false;
    private JavaPluginLoader pluginLoader;
    private PluginMeta meta;
    private Logger logger;

    public JavaPluginContainer(final @Nonnull File jarFile) {
        Apsaras.injector().inject(this);
        this.jarFile = jarFile;
    }

    @Override
    public @Nonnull
    PluginMeta meta() {
        return this.meta;
    }

    @Override
    public Logger logger() {
        return this.logger;
    }

    @Override
    public void addDepend(final @Nonnull PluginContainer depend) {
        if (!(depend instanceof JavaPluginContainer)) {
            throw new IllegalArgumentException("Only java plugin could depend on.");
        }
        this.pluginLoader.addDepend(((JavaPluginContainer) depend).pluginLoader);
    }

    @Override
    public boolean enabled() {
        return this.enable;
    }

    @Override
    public void load() throws Exception {
        this.pluginLoader = new JavaPluginLoader(jarFile.toURI().toURL(), Apsaras.server().classLoader());
        InputStream metaStream = this.pluginLoader.getResourceAsStream("apsaras.json");
        if (metaStream == null) {
            throw new FileNotFoundException("No apsaras.json found in " + jarFile);
        }
        InputStreamReader metaReader = new InputStreamReader(metaStream);
        this.meta = new Gson().fromJson(metaReader, ImplPluginMeta.class);
        metaReader.close();
        metaStream.close();

        for (PluginDepend depend : this.meta.depends()) {
            if(depend.type().equals(PluginDepend.Type.LIBRARY)){
                this.pluginLoader.addURL(
                        dependencyResolver.getDependencyFile(depend.name()).toURI().toURL()
                );
            }
        }
        eventManager.post(new ImplPluginLifeEvent.Load(this));
    }

    @Override
    public void enable() throws Exception {
        this.enable = true;
        this.logger = new ImplPrefixLogger(Apsaras.server().logger(), "[" + this.name() + "]: ");
        Class<?> mainClass = this.pluginLoader.loadClass(this.meta.main());
        Object main = ((ImplInjector) Apsaras.injector())
                .injector()
                .createChildInjector(binder -> {
                    binder.bind(PluginContainer.class).toInstance(this);
                    binder.bind(Logger.class).toInstance(this.logger);
                })
                .getInstance(mainClass);
        this.eventManager.registerListeners(this, main);
        eventManager.post(new ImplPluginLifeEvent.Enable(this));
    }

    @Override
    public void disable() throws Exception {
        this.eventManager.unregisterPluginListeners(this);
    }

    @Override
    public Path dataPath() {
        Path dataPath = server.pluginPath().resolve("storage").resolve(this.name());
        try {
            Files.createDirectories(dataPath);
        } catch (IOException e) {
            logger.warn("Failed to create data directory for " + this.name() + ".", e);
        }
        return dataPath;
    }

    @Override
    public Path configPath() {
        Path dataPath = server.pluginPath().resolve("config").resolve(this.name());
        try {
            Files.createDirectories(dataPath);
        } catch (IOException e) {
            logger.warn("Failed to create config directory for " + this.name() + ".", e);
        }
        return server.pluginPath().resolve("config").resolve(this.name());
    }

    @Override
    public @Nonnull
    String name() {
        return this.meta == null ? this.jarFile.getName() : this.meta.name();
    }

    @Override
    public String toString() {
        return this.name();
    }
}
