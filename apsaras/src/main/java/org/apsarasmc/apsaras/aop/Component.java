package org.apsarasmc.apsaras.aop;

import java.lang.annotation.*;

@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.TYPE)
@Inherited
public @interface Component {
}