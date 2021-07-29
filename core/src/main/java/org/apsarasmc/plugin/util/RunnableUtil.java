package org.apsarasmc.plugin.util;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public class RunnableUtil {
    private RunnableUtil() {

    }

    public static <T> Runnable runnable(Callable<T> command, CompletableFuture<T> future) {
        return () -> {
            try {
                future.complete(command.call());
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        };
    }
}
