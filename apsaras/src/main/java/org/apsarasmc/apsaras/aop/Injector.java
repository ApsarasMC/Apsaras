package org.apsarasmc.apsaras.aop;

import javax.inject.Provider;
import javax.inject.Scope;
import java.lang.annotation.Annotation;
import java.util.Map;

public interface Injector {
  void injectMembers(Object instance);
  <T> Provider<T> getProvider(Class<T> type);
  <T> T getInstance(Class<T> type);
  Injector getParent();
}
