package org.apsarasmc.spigot;

import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.Server;
import org.apsarasmc.apsaras.scheduler.SchedulerService;
import org.apsarasmc.plugin.ImplGame;
import org.apsarasmc.spigot.event.Handlers;
import org.apsarasmc.spigot.scheduler.SyncScheduler;
import org.apsarasmc.spigot.scheduler.UtsScheduler;
import org.apsarasmc.spigot.util.SpigotLogger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class SpigotCore implements Server, Listener {
    private JavaPlugin wrapper;
    @Inject
    private SyncScheduler syncScheduler;
    @Inject
    private UtsScheduler utsScheduler;
    @Inject
    private Handlers handlers;

    public SpigotCore(final JavaPlugin wrapper) {
        this.wrapper = wrapper;
    }

    public JavaPlugin wrapper() {
        return this.wrapper;
    }

    public void init() {
        new ImplGame(new SpigotModule(binder -> {
            binder.bind(Server.class).toInstance(this);
            binder.bind(SpigotCore.class).toInstance(this);
        }));
        Apsaras.injector().inject(this);

        try {
            Path pluginsPath = this.pluginPath().resolve("plugins");
            Files.createDirectories(pluginsPath);
            Arrays.stream(
                    Objects.requireNonNull(pluginsPath.toFile().listFiles())
            ).filter(file -> file.getName().endsWith(".jar")).forEach(file -> {
                Apsaras.pluginManager().addPlugin(file);
            });
        } catch (Exception e) {
            this.logger().warn("Failed to open plugins dir.", e);
        }
        Apsaras.pluginManager().load();
        Apsaras.pluginManager().enable();

        Bukkit.getPluginManager().registerEvents(this, this.wrapper);
        this.handlers.register();
    }

    @Override
    public SchedulerService sync() {
        return this.syncScheduler;
    }

    @Override
    public SchedulerService uts() {
        return this.utsScheduler;
    }

    @Override
    public Logger logger() {
        return new SpigotLogger(wrapper.getLogger());
    }

    @Override
    public ClassLoader classLoader() {
        return SpigotCore.class.getClassLoader();
    }

    @Override
    public Path gamePath() {
        return new File(".").toPath();
    }

    @Override
    public Path pluginPath() {
        return this.gamePath().resolve("apsaras");
    }

    @Override
    public String version() {
        return wrapper.getDescription().getVersion();
    }
}
