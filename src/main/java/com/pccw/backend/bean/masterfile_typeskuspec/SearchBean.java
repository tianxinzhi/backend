package com.pccw.backend.bean.masterfile_typeskuspec;


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
    private String typeId;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String skuId;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String specId;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String active;
    
}