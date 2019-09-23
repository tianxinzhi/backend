package com.pccw.backend.bean.masterfile_repo;


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
 @ApiModel(value = "Shop 模块 - SearchBean", description = "")
public class SearchBean extends BaseSearchBean {

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="门店编码",name="repoCode",example="")
    private String repoCode;

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="门店名称",name="repoName",example="")
    private String repoName;

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="门店地址",name="repoAddr",example="")
    private String repoAddr;

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="areaId",name="areaId",example="")
    private String areaId;

    //private String rightDesc;
    
}