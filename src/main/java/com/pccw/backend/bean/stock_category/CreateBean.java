package com.pccw.backend.bean.stock_category;


import com.pccw.backend.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
 @ApiModel(value="Sku_Repo模块 - CreateBean",description="")
public class CreateBean extends BaseBean {

    @ApiModelProperty(value="商店ID",name="repoId",example="")
    private Long repoId;

    @ApiModelProperty(value="skuId",name="skuId",example="")
    private Long skuId;

    @ApiModelProperty(value="商品类型ID",name="stockTypeId",example="")
    private Long stockTypeId;

    @ApiModelProperty(value="商品数量",name="qty",example="")
    private Integer qty;

    @ApiModelProperty(value="itemId",name="itemId",example="")
    private String itemId;


    
}