package com.pccw.backend.bean.masterfile_attr;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchBean;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchBean extends BaseSearchBean {

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String attrName;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String attrValueType;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String active;

}
