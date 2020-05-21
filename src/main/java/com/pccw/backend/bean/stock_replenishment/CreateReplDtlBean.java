package com.pccw.backend.bean.stock_replenishment;


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
 @ApiModel(value="StockReplenishment 模块 - CreateReplDtlBean",description="")
public class CreateReplDtlBean extends BaseBean {

    @ApiModelProperty(value="dtlSkuId",name="dtlSkuId",example="")
    private long dtlSkuId;

    @ApiModelProperty(value="dtlItemId",name="dtlItemId",example="")
    private long dtlItemId;

    @ApiModelProperty(value="仓库Id",name="dtlRepoId",example="")
    private long dtlRepoId;

    @ApiModelProperty(value="数量",name="dtlQty",example="")
    private long dtlQty;

    @ApiModelProperty(value="交易号",name="logTxtBum",example="")
    private String logTxtBum;

    @ApiModelProperty(value="dtlLogId",name="dtlLogId",example="")
    private long dtlLogId;

    @ApiModelProperty(value="业务动作",name="dtlAction",example="")
    private String dtlAction;

    @ApiModelProperty(value="dtlSubin",name="dtlSubin",example="")
    private String dtlSubin;

    @ApiModelProperty(value="lisStatus",name="lisStatus",example="")
    private String lisStatus;

    @ApiModelProperty(value="lisResult",name="lisResult",example="")
    private String lisResult;

    @ApiModelProperty(value="Status",name="Status",example="")
    private String Status;






}