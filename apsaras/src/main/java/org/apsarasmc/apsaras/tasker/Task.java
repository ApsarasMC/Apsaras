package org.apsarasmc.apsaras.tasker;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Task< T > {
  T get();

  < R > Task< R > then(Function< T, R > command);

  Task<Void> then(Consumer<T> command);

  Task< Void > then(Runnable command);

  <R> Task< R > then(Callable<R> command);

  boolean isDone();

  boolean hasException();

  T getWithException() throws Exception;
}
