package org.apsarasmc.apsaras.aop;

import org.apsarasmc.apsaras.plugin.PluginDepend;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention (RetentionPolicy.RUNTIME)
public @interface Dependency {
  PluginDepend.Type type() default PluginDepend.Type.REQUIRE;

  String name();
}
