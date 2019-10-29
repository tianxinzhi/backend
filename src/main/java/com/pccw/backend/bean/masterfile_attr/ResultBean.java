package com.pccw.backend.bean.masterfile_attr;

import com.pccw.backend.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="Attr模块 - EditBean",description="attr搜索返回的结果")
public class ResultBean extends BaseBean {

    @ApiModelProperty(value="属性值",name="attrName",example="color")
    private String attrName;

    @ApiModelProperty(value="属性值",name="attrDesc",example="color(iphone)")
    private String attrDesc;

    @ApiModelProperty(value="属性值",name="attrValueNames",example="['red','black']")
    private String[] attrValueNames;

    @ApiModelProperty(value="属性值",name="attrValues",example="[1,2]")
    private long[] attrValues;
}

