package com.pccw.backend.bean.auth_role;

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
public class SearchBean extends BaseSearchBean {

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String roleName;

    private String roleDesc;

    private long id;

    private long[] rightId;

    private String[] rightName;
}