package org.apsarasmc.plugin.event;

import org.apsarasmc.apsaras.event.EventListener;
import org.apsarasmc.apsaras.plugin.PluginContainer;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

public class ImplEventListener< T > implements EventListener< T > {
  private final @Nonnull
  PluginContainer pluginContainer;
  private final @Nonnull
  Object object;
  private final @Nonnull
  Method method;

  public ImplEventListener(final @Nonnull PluginContainer pluginContainer,
                           final @Nonnull Object object,
                           final @Nonnull Method method) {
    this.pluginContainer = pluginContainer;
    this.object = object;
    this.method = method;
  }

  @Nonnull
  public PluginContainer pluginContainer() {
    return pluginContainer;
  }

  @Nonnull
  public Object object() {
    return object;
  }

  @Nonnull
  public Method method() {
    return method;
  }

  @Override
  public void handle(T event) throws Exception {
    method.invoke(object, event);
  }
}
