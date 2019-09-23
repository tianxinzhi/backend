package com.pccw.backend.bean.masterfile_class;


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
    private String className;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String classDesc;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String classType;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String parentClassId;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String active;
    
}