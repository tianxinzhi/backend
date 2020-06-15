package com.pccw.backend.bean.masterfile_repo;


import com.pccw.backend.bean.BaseBean;
import com.pccw.backend.entity.DbResSku;
import com.pccw.backend.entity.DbResStockType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
 @ApiModel(value="SkuRepo模块 - CreateBean",description="")
public class StockCreateBean extends BaseBean {

    @ApiModelProperty(value="item",name="item",example="")
    private long sku;

    @ApiModelProperty(value="subinventory",name="subinventory",example="")
    private long stockType;

    @ApiModelProperty(value="qty",name="qty",example="")
    private long qty;

    @ApiModelProperty(value="resQty",name="resQty",example="")
    private Long resQty;
}
