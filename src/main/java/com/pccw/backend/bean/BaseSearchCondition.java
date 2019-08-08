package com.pccw.backend.bean;

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
public class BaseSearchCondition implements Serializable{


    @NotNull
    private Integer pageIndex=0;
    @NotNull
    private Integer pageSize=10;



}