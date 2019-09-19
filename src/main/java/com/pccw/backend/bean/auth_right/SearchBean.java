package com.pccw.backend.bean.auth_right;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchBean;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
@ApiModel(value="Right模块 - SearchBean",description="")
public class SearchBean extends BaseSearchBean {

    // @NotBlank(message = "XXXXXXXXXXX")
    // @Size(min=2,max=4,message = "YYYYYYYYY")
    @PredicateAnnotation(type = PredicateType.LIKE) 
    private String rightName; 

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String rightUrl;

    @PredicateAnnotation(type = PredicateType.LIKE)
    private String rightPid;

    private String rightDesc;
    
}