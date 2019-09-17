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

    @ApiModelProperty(value="class",name="classId",example="delete")
    private String classId;

    @ApiModelProperty(value="skuCode",name="skuCode",example="delete")
    private String skuCode;

    @ApiModelProperty(value="skuDesc",name="skuDesc",example="delete")
    private String skuDesc;

    @ApiModelProperty(value="skuName",name="skuName",example="delete")
    private String skuName;

}
