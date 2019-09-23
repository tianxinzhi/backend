package com.pccw.backend.bean.masterfile_type;


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
 @ApiModel(value = "Type 模块 - SearchBean", description = "")
public class SearchBean extends BaseSearchBean {

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="是否有效",name="active",example="")
    private String active;

    @ApiModelProperty(value="类型名称",name="typeName",example="")
    @PredicateAnnotation(type = PredicateType.LIKE)
    private String typeName;

    @ApiModelProperty(value="类型编码",name="typeCode",example="")
    @PredicateAnnotation(type = PredicateType.LIKE)
    private String typeCode;

    @ApiModelProperty(value="序列号",name="sequential",example="")
    @PredicateAnnotation(type = PredicateType.LIKE)
    private String sequential;

    @ApiModelProperty(value="类型描述",name="typeDesc",example="")
    @PredicateAnnotation(type = PredicateType.LIKE)
    private String typeDesc;
    
}