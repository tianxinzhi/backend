package com.pccw.backend.bean.masterfile_spec;


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
public class SearchBean extends BaseSearchBean {

    @PredicateAnnotation(type = PredicateType.LIKE) 
    private String specName;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String specDesc;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String active;

    
}