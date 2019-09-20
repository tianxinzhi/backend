package com.pccw.backend.bean.masterfile_attr_value;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchBean;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchBean extends BaseSearchBean {

//    @PredicateAnnotation(type = PredicateType.LIKE)
//    private String attrId;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String attrValue;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String unitOfMeasure;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String valueFrom;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String valueTo;

}
