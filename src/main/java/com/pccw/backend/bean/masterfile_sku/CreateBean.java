package com.pccw.backend.bean.masterfile_sku;

import com.pccw.backend.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value="Sku模块 - CreateBean",description="")
public class CreateBean extends BaseBean {

    @ApiModelProperty(value="repos",name="repos",example="")
    private long stores;

    @ApiModelProperty(value="skuCode",name="sku编码",example="")
    private String skuCode;

    @ApiModelProperty(value="skuDesc",name="sku详情",example="")
    private String skuDesc;

    @ApiModelProperty(value="skuName",name="sku名称",example="")
    private String skuName;

    @ApiModelProperty(value="qty",name="qty",example="")
    private long qty;

    @ApiModelProperty(value="type",name="type",example="")
    private long type;

    @ApiModelProperty(value="spec",name="spec",example="")
    private long spec;

    @ApiModelProperty(value="attrs",name="attr值",example="[2，3]")
    private long[] attrs;

    @ApiModelProperty(value="attrValueList",name="attrValue值",example="{[4,5],[4,6]}")
    private List<int[]> attrValueList;
//    private Attr attr

}
