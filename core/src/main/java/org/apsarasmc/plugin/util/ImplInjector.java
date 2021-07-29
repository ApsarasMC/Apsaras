package org.apsarasmc.plugin.util;

import org.apsarasmc.apsaras.Injector;

public class ImplInjector implements Injector {
    private final com.google.inject.Injector injector;

    public ImplInjector(final com.google.inject.Injector injector) {
        this.injector = injector;
    }

    @Override
    public <T> T inject(final T object) {
        injector.injectMembers(object);
        return object;
    }

    @Override
    public <T> T getInstance(final Class<T> clazz) {
        return injector.getInstance(clazz);
    }

    public com.google.inject.Injector injector() {
        return this.injector;
    }
}
