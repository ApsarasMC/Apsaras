package org.apsarasmc.plugin.plugin;

import org.apsarasmc.apsaras.plugin.PluginDepend;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ImplPluginDepend implements PluginDepend {
    private final @Nonnull
    PluginDepend.Type type;
    private final @Nonnull
    String name;

    private ImplPluginDepend(final @Nonnull PluginDepend.Type type, final @Nonnull String name) {
        this.type = type;
        this.name = name;
    }

    public static ImplPluginDepend clone(PluginDepend depend) {
        if (depend instanceof ImplPluginDepend) {
            return (ImplPluginDepend) depend;
        }
        return new ImplPluginDepend(depend.type(), depend.name());
    }

    @Nonnull
    @Override
    public PluginDepend.Type type() {
        return this.type;
    }

    @Nonnull
    @Override
    public String name() {
        return this.name;
    }

    public static class Builder implements PluginDepend.Builder {
        private PluginDepend.Type type;
        private String name;

        @Override
        public PluginDepend.Builder type(final @Nonnull PluginDepend.Type type) {
            Objects.requireNonNull(type, "type");
            this.type = type;
            return this;
        }

        @Override
        public PluginDepend.Builder name(final @Nonnull String name) {
            Objects.requireNonNull(name, "name");
            this.name = name;
            return this;
        }

        @Override
        public PluginDepend build() {
            return new ImplPluginDepend(this.type, this.name);
        }
    }
}
