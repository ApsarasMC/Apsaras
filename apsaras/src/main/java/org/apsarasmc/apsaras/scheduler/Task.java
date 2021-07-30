package org.apsarasmc.apsaras.scheduler;

import java.util.function.Function;

public interface Task< T > {
  T get();

  < R > Task< R > then(Function< T, R > command);

  boolean isDone();

  boolean hasException();

  T getWithException() throws Throwable;
}
