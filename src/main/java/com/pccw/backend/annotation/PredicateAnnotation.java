package com.pccw.backend.annotation;


import java.lang.annotation.*;
 

/**
 * PredicateType
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PredicateAnnotation {
    public PredicateType type();    
}