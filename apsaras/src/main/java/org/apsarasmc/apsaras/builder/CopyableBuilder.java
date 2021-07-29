package org.apsarasmc.apsaras.builder;

public interface CopyableBuilder<T, B> extends AbstractBuilder<T, B> {
    B from(T from);
}
