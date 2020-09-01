package com.pccw.backend.bean.stock_return;

import com.pccw.backend.bean.BaseSearchBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value="Stock_return",description="")
public class SearchBean extends BaseSearchBean {

    @ApiModelProperty(value="SkuId",name="",example="")
    private List<String> sku;

    @ApiModelProperty(value="fromChannel",name="",example="")
    private Long fromChannel;

    @ApiModelProperty(value="toWareHouse",name="",example="")
    private Long toWareHouse;

    @ApiModelProperty(value="returnDate",name="",example="")
    private Long returnDate;
}
