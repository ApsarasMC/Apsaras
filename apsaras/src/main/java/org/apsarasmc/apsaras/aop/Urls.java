package org.apsarasmc.apsaras.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention (RetentionPolicy.RUNTIME)
public @interface Urls {
  String home() default "";

  String issue() default "";
}
