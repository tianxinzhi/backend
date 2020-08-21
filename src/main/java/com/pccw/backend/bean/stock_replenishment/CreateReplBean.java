package com.pccw.backend.bean.stock_replenishment;


import com.pccw.backend.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
 @ApiModel(value="StockReplenishment 模块 - CreateReplBean",description="")
public class CreateReplBean extends BaseBean {

    @ApiModelProperty(value="",name="fromChannelId",example="")
    private Long fromChannelId;

    @ApiModelProperty(value="",name="toChannelId",example="")
    private Long toChannelId;

    @ApiModelProperty(value="",name="fromChannelName",example="")
    private String fromChannelName;

    @ApiModelProperty(value="",name="toChannelName",example="")
    private String toChannelName;

    @ApiModelProperty(value="",name="skuId",example="")
    private Long skuId;

    @ApiModelProperty(value="",name="stock",example="")
    private Long stock;

    @ApiModelProperty(value="",name="qty",example="")
    private Long qty;

    @ApiModelProperty(value="",name="lastReplenish",example="")
    private Long lastReplenish;

    @ApiModelProperty(value="",name="suggestedQty1",example="")
    private Long suggestedQty1;

    @ApiModelProperty(value="",name="suggestedQty2",example="")
    private Long suggestedQty2;

    @ApiModelProperty(value="",name="suggestedQty3",example="")
    private Long suggestedQty3;

    @ApiModelProperty(value="",name="logTxtNum",example="")
    private String logTxtNum;
}
