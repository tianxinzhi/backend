package com.pccw.backend.bean.masterfile_spec_attr;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchBean;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchBean extends BaseSearchBean {

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String specId;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String verId;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String attrId;

}
