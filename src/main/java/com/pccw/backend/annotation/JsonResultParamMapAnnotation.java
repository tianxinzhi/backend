package com.pccw.backend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonResultParamMapAnnotation {
    public String param1() default "";
    public String param2() default "";
    public String param3() default "";
}
