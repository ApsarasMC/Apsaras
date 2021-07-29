package org.apsarasmc.apsaras.event;

public interface EventListener<T> {
    void handle(T event) throws Exception;
}
