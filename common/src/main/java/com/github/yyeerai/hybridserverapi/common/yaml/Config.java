package com.github.yyeerai.hybridserverapi.common.yaml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark fields that are to be used as configuration values.
 * <p>
 * The value of the annotation is the key of the configuration value. If the value is not present, the field name
 * will be used as the key.
 * <p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Config {
    String value() default "";

    boolean isList() default false;
}