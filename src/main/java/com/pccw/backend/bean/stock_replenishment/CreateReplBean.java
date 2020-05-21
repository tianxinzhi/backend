package com.pccw.backend.bean.stock_replenishment;


import com.pccw.backend.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
 @ApiModel(value="StockReplenishment 模块 - CreateReplBean",description="")
public class CreateReplBean extends BaseBean {

    @ApiModelProperty(value="补货批次",name="logBatchId",example="")
    private long logBatchId;

    @ApiModelProperty(value="交货单号",name="logDNNum",example="")
    private long logDNNum;

    @ApiModelProperty(value="交易号",name="logTxtBum",example="")
    private String logTxtBum;

    @ApiModelProperty(value="业务类型",name="logType",example="")
    private String logType;

    @ApiModelProperty(value="订单方式",name="logOrderNature",example="")
    private String logOrderNature;

    @ApiModelProperty(value="状态",name="status",example="")
    private String status;

    @ApiModelProperty(value="备注",name="remark",example="")
    private String remark;

    @ApiModelProperty(value="商店code",name="ccc",example="")
    private String ccc;

    @ApiModelProperty(value="Work order",name="wo",example="")
    private String wo;

    @ApiModelProperty(value="warehouseID",name="repoIdFrom",example="")
    private long repoIdFrom;

    @ApiModelProperty(value="shop ID",name="repoIdTo",example="")
    private long repoIdTo;

    private long logRepoIn;

    private Long logRepoOut;

    private String replType;

    private List<CreateReplDtlBean> line;






}