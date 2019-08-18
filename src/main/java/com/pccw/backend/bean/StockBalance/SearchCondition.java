package com.pccw.backend.bean.stockbalance;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchCondition;

import lombok.Data;
import lombok.NoArgsConstructor;




/**
 * SearchCondition
 */

@Data
@NoArgsConstructor
public class SearchCondition extends BaseSearchCondition implements Serializable{

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String skuNum; // same as entity property and relative to the data clomun

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String itemNum;
    

    @PredicateAnnotation(type = PredicateType.EQUEL)
    @NotEmpty
    private String repoNum;

}