package com.pccw.backend.bean.StockBalance;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;



import lombok.Data;




/**
 * SearchCondition
 */

@Data
public class SearchCondition implements Serializable{

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String skuNum; // same as entity property and relative to the data clomun

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String itemNum;
    

    @PredicateAnnotation(type = PredicateType.EQUEL)
    @NotEmpty
    private String repoNum;

    @NotNull
    private Integer pageIndex=0;
    @NotNull
    private Integer pageSize=10;



}