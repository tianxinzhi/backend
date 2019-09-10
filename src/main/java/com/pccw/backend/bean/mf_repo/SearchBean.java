package com.pccw.backend.bean.mf_repo;


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
    private String repoCode;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String repoName;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String repoAddr;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String areaId;

    //private String rightDesc;
    
}