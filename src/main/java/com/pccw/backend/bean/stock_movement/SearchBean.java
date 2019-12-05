package com.pccw.backend.bean.stock_movement;


import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
 @ApiModel(value = "Process 模块 - SearchBean", description = "")
public class SearchBean extends BaseSearchBean {

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="状态",name="status",example="")
    private String status;

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="Nature",name="logOrderNature",example="")
    private String logOrderNature;

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="商店Id",name="repoId",example="")
    private Long repoId;

//    @PredicateAnnotation(type = PredicateType.BETWEEN)
//    @ApiModelProperty(value="日期",name="creatAt",example="")
//    private List createAtRange;
    
}