package com.pccw.backend.bean.masterfile_type;


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
    private String active;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String typeName;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String typeCode;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String sequential;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String typeDesc;
    
}