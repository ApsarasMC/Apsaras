package org.apsarasmc.plugin.plugin;

import org.apsarasmc.apsaras.plugin.PluginDepend;
import org.apsarasmc.apsaras.plugin.PluginMeta;
import org.apsarasmc.apsaras.plugin.PluginUrls;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class ImplPluginMeta implements PluginMeta {
    private final @Nonnull
    String name;
    private final @Nullable
    String describe;
    private final @Nullable
    String version;
    private final @Nullable
    ImplPluginUrls urls;
    private final @Nullable
    String main;
    private final @Nonnull
    Collection<ImplPluginDepend> depends;

    private ImplPluginMeta(final @Nonnull String name, final @Nullable String describe,
                           final @Nullable String version, final @Nullable PluginUrls urls,
                           final @Nullable String main, final @Nonnull Collection<PluginDepend> depends) {
        this.name = name;
        this.describe = describe;
        this.version = version;
        this.urls = ImplPluginUrls.clone(urls);
        this.main = main;
        this.depends = depends.stream()
                .map(ImplPluginDepend::clone)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public @Nonnull
    String name() {
        return this.name;
    }

    @Override
    public @Nullable
    String describe() {
        return this.describe;
    }

    @Override
    public @Nullable
    String version() {
        return this.version;
    }

    @Override
    public @Nullable
    PluginUrls urls() {
        return this.urls;
    }

    @Override
    @Nullable
    public String main() {
        return main;
    }

    @Nonnull
    @Override
    public Collection<PluginDepend> depends() {
        return depends.stream().map(d -> (PluginDepend) d).collect(Collectors.toCollection(ArrayList::new));
    }

    public static final class Builder implements PluginMeta.Builder {
        private final Collection<PluginDepend> depends = new ArrayList<>();
        private String name;
        private String describe;
        private String version;
        private PluginUrls urls;
        private String main;

        @Override
        public PluginMeta.Builder name(final @Nonnull String name) {
            Objects.requireNonNull(name, "name");
            this.name = name;
            return this;
        }

        @Override
        public PluginMeta.Builder describe(final @Nullable String describe) {
            this.describe = describe;
            return this;
        }

        @Override
        public PluginMeta.Builder version(final @Nullable String version) {
            this.version = version;
            return this;
        }

        @Override
        public PluginMeta.Builder urls(final @Nullable PluginUrls urls) {
            this.urls = urls;
            return this;
        }

        @Override
        public PluginMeta.Builder main(final @Nullable String main) {
            this.main = main;
            return this;
        }

        @Override
        public PluginMeta.Builder depend(PluginDepend depend) {
            Objects.requireNonNull(depend, "depend");
            this.depends.add(depend);
            return this;
        }

        @Override
        public PluginMeta build() {
            Objects.requireNonNull(name, "name");
            Objects.requireNonNull(main, "main");
            return new ImplPluginMeta(name, describe, version, urls, main, depends);
        }
    }
}
