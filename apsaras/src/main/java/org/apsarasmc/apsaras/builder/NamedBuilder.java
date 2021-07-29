package org.apsarasmc.apsaras.builder;

import org.apsarasmc.apsaras.util.Named;

public interface NamedBuilder<T extends Named, B> extends AbstractBuilder<T, B> {
    B name(String name);
}
