package com.pccw.backend.annotation;

import com.pccw.backend.bean.LabelAndValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LabelAndValueAnnotation {
    public String Value() default "";
    public String Label() default "";
    public String Other() default "";
}
