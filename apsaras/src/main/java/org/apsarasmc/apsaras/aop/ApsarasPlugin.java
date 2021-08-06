package org.apsarasmc.apsaras.aop;

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
}