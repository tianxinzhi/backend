package com.pccw.backend.bean.stock_category;


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
 @ApiModel(value = "TypeSkuSpec 模块 - SearchBean", description = "")
public class SearchBean extends BaseSearchBean {

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="商店ID",name="repoId",example="")
    private String repoId;

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="skuId",name="skuId",example="")
    private String skuId;

    private String repoName;
    private String skuName;
    private String stockTypeName;
    private Long qty;
    private String skuDesc;
    private Long id;
    
}