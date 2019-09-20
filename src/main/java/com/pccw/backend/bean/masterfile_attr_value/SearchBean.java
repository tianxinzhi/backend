package com.pccw.backend.bean.masterfile_attr_value;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="Attr_Value模块 - SearchBean",description="")
public class SearchBean extends BaseSearchBean {

//    @PredicateAnnotation(type = PredicateType.LIKE)
//    private String attrId;

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="属性值",name="attrValue",example="delete")
    private String attrValue;

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="单位",name="unitOfMeasure",example="delete")
    private String unitOfMeasure;

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="来源",name="valueFrom",example="delete")
    private String valueFrom;

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="作用",name="valueTo",example="delete")
    private String valueTo;

}
