package com.pccw.backend.bean.masterfile_sku;

import com.pccw.backend.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="Sku模块 - CreateBean",description="")
public class CreateBean extends BaseBean {

    @ApiModelProperty(value="class",name="分类",example="")
    private String classId;

    @ApiModelProperty(value="skuCode",name="sku编码",example="")
    private String skuCode;

    @ApiModelProperty(value="skuDesc",name="sku详情",example="")
    private String skuDesc;

    @ApiModelProperty(value="skuName",name="sku名称",example="")
    private String skuName;

}
