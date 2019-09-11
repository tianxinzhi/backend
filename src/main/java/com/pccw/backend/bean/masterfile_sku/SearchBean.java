package com.pccw.backend.bean.masterfile_sku;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchBean;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchBean extends BaseSearchBean {

    //@PredicateAnnotation(type = PredicateType.EQUEL)
    //private String classId;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String skuName;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String skuCode;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String skuDesc;
}
