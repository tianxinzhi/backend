package com.pccw.backend.bean.authright;

import java.io.Serializable;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchBean;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
public class SearchBean extends BaseSearchBean implements Serializable{

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String rightName;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String rightUrl;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String rightModule;
    
}