package com.pccw.backend.bean.masterfile_sku;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value="Sku模块 - SkuResultBean",description="sku搜索结果")
public class ResultBean {

    @ApiModelProperty(value="spec",name="spec",example="1")
    private long type;

    @ApiModelProperty(value="typeName",name="typeName",example="iphone")
    private String typeName;

    @ApiModelProperty(value="spec",name="spec",example="1")
    private long spec;

    @ApiModelProperty(value="specName",name="specName",example="peizhi")
    private String specName;

    @ApiModelProperty(value="attrs",name="attrs",example="[2,3]")
    private long[] attrs;

    @ApiModelProperty(value="attrNames",name="attrNames",example="['color','shape']")
    private String[] attrNames;

    @ApiModelProperty(value="attrValues",name="attrValues",example="[['4','6'],['5','7']]")
    private List<String[]> attrValues;

    @ApiModelProperty(value="attrValueNames",name="attrValueNames",example="[['red','green'],['red','blue']]")
    private List<String[]> attrValueNames;
}
