package org.apsarasmc.apsaras.aop;

import org.apsarasmc.apsaras.plugin.PluginDepend;

import java.lang.annotation.*;

@Retention (RetentionPolicy.SOURCE)
@Target (ElementType.TYPE)
@Inherited
public @interface ApsarasPlugin {
  String name();

  String describe() default "";

  String version() default "";

  Urls urls() default @Urls;

  Dependency[] dependency() default { };

  @interface Dependency {
    PluginDepend.Type type() default PluginDepend.Type.REQUIRE;

    String name();
  }

  @interface Urls {
    String home() default "";

    String issue() default "";
  }
}