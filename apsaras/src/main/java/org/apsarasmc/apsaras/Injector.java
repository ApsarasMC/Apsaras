package org.apsarasmc.apsaras;

public interface Injector {
  < T > T inject(T object);

  < T > T getInstance(Class< T > clazz);
}
