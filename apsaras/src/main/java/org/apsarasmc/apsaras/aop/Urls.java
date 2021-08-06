package org.apsarasmc.apsaras.aop;

import java.lang.annotation.*;

@Retention (RetentionPolicy.RUNTIME)
public @interface Urls {
  String home() default "";

  String issue() default "";
}
