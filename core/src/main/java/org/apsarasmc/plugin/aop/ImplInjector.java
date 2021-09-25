package org.apsarasmc.plugin.aop;

import jdk.dynalink.beans.StaticClass;
import org.apsarasmc.apsaras.aop.Injector;

import javax.inject.Provider;

public class ImplInjector implements Injector {
  private final com.google.inject.Injector handle;

  public ImplInjector(final com.google.inject.Injector handle) {
    this.handle = handle;
  }

  @Override
  public void injectMembers(Object instance) {
    handle.injectMembers(instance);
  }

  @Override
  public < T > Provider< T > getProvider(Class< T > type) {
    return handle.getProvider(type);
  }
  public < T > Provider< T > getProvider(StaticClass type) {
    return getProvider((Class< T >) type.getRepresentedClass());
  }

  @Override
  public < T > T getInstance(Class< T > type) {
    return handle.getInstance(type);
  }
  public < T > T getInstance(StaticClass type) {
    return getInstance((Class< T >) type.getRepresentedClass());
  }

  @Override
  public Injector getParent() {
    return new ImplInjector(handle.getParent());
  }

  public com.google.inject.Injector handle() {
    return handle;
  }
}
