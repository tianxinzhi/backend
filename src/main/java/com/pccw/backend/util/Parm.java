package com.pccw.backend.util;

import com.pccw.backend.annotation.PredicateType;

import lombok.Data;

/**
 * Parm
 */
@Data
public class Parm {
    private PredicateType predicateType;
    private String name;
    private Object value;
    public Parm(PredicateType pt, String name, Object o){
        this.predicateType = pt;
        this.name=name;
        this.value=o;
    }
    
}