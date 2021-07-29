package org.apsarasmc.plugin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.apsaras.Server;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.apsarasmc.apsaras.plugin.PluginMeta;
import org.apsarasmc.apsaras.plugin.PluginUrls;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Singleton
public class ApsarasPluginContainer implements PluginContainer {
    private PluginMeta meta;
    private boolean enable = false;

    @Inject
    private Server server;

    @Nonnull
    @Override
    public PluginMeta meta() {
        return this.meta;
    }

    @Override
    public Logger logger() {
        return Apsaras.server().logger();
    }

    @Override
    public void addDepend(final PluginContainer depend) {
        //
    }

    @Override
    public boolean enabled() {
        return this.enable;
    }

    @Override
    public void load() throws Exception {
        this.meta = PluginMeta.builder()
                .name("apsaras")
                .version("1.0-SNAPSHOT")
                .urls(PluginUrls.builder()
                        .home("https://github.com/ApsarasMC")
                        .issue("https://github.com/ApsarasMC/Apsaras/issues")
                        .build())
                .main("org.apsarasmc.plugin.ApsarasPluginContainer")
                .build();
    }

    @Override
    public void enable() throws Exception {
        this.enable = true;
    }

    @Override
    public void disable() throws Exception {
        throw new IllegalStateException("Apsaras Self PluginContainer couldn't be disable.");
    }

    @Override
    public Path dataPath() {
        Path dataPath = server.pluginPath().resolve("storage").resolve(this.name());
        try {
            Files.createDirectories(dataPath);
        } catch (IOException e) {
            this.logger().warn("Failed to create data directory for " + this.name() + ".", e);
        }
        return dataPath;
    }

    @Override
    public Path configPath() {
        Path dataPath = server.pluginPath().resolve("config").resolve(this.name());
        try {
            Files.createDirectories(dataPath);
        } catch (IOException e) {
            this.logger().warn("Failed to create config directory for " + this.name() + ".", e);
        }
        return server.pluginPath().resolve("config").resolve(this.name());
    }

    @Nonnull
    @Override
    public String name() {
        return this.meta == null ? "apsaras" : this.meta.name();
    }

    @Override
    public String toString() {
        return this.name();
    }
}
