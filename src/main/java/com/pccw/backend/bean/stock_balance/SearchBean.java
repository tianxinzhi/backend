package com.pccw.backend.bean.stock_balance;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchBean;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * SearchCondition
 */

@Data
@NoArgsConstructor
public class SearchBean extends BaseSearchBean implements Serializable{

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String skuNum; // same as entity property and relative to the data clomun

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String itemNum;
    

    @PredicateAnnotation(type = PredicateType.EQUEL)
    @NotEmpty
    private String repoNum;

}