package com.pccw.backend.bean.AuthRole;

import java.io.Serializable;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchCondition;

import lombok.Data;

/**
 * SearchCondition
 */

 @Data
public class SearchCondition extends BaseSearchCondition implements Serializable{

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String roleName;
    
}