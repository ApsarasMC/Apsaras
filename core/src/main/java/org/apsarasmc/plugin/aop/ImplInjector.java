package org.apsarasmc.plugin.aop;

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

  @Override
  public < T > T getInstance(Class< T > type) {
    return handle.getInstance(type);
  }

  @Override
  public Injector getParent() {
    return new ImplInjector(handle.getParent());
  }

  public com.google.inject.Injector handle() {
    return handle;
  }
}
