package com.pccw.backend.bean.nr_normal_reserve;

import com.pccw.backend.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value="NR_Normal_Reserve - CreateBean",description="")
public class CreateBean extends BaseBean {

    // POS / BOM / BES
    @ApiModelProperty(value="Normal_Reserve",name="order_system",example="")
//    private String logSys;
    private String order_system;

    // OrderId
    @ApiModelProperty(value="Normal_Reserve",name="order_id",example="")
//    private String logOrderId;
    private String order_id;

    // Related OrderId
    @ApiModelProperty(value="Normal_Reserve",name="sales_id",example="")
//    private String logRelatedOrderId;
    private String sales_id;

    // N(normal) / A(Advance Order)
    @ApiModelProperty(value="Normal_Reserve",name="logOrderType",example="")
    private String logOrderType;

    // Transation Number made from SMP self
    @ApiModelProperty(value="Normal_Reserve",name="logTxtBum",example="")
    private String logTxtBum;

    // O - Order / M - Mangement / R - Repl
    @ApiModelProperty(value="Normal_Reserve",name="logType",example="")
    private String logType;

    // ASG(Assign) / RET(return) / EXC(Exchange)
    // ARS(Advanced Reserve) / CARS(Cancel advance reserve) / APU(Advance pick up)
    // RREQ(Replenishment request)
    // SOTS(stock out to shop)/ SIFS(stock in from shop)
    // SOTW(stock out to warehouse)/ SIFW(stock in from warehouse)
    // SIWPO(stock in without PO)
    // ST(Stock Take) / STA(Stock Take Adjustment)
    // SCC(stock change category)
    @ApiModelProperty(value="Normal_Reserve",name="sequest_nature",example="")
//    private String logOrderNature;
    private String request_nature;

    // W - waiting LIS to handle / D - Done
    @ApiModelProperty(value="Normal_Reserve",name="status",example="")
    private String status;

    // remark
    @ApiModelProperty(value="Normal_Reserve",name="remark",example="")
    private String remarks;

    // Shop code mapping
    @ApiModelProperty(value="Normal_Reserve",name="ccc",example="")
    private String ccc;

    //  Transaction date
    @ApiModelProperty(value="Normal_Reserve",name="wo",example="")
    private String wo;

    //  Business date 根据传入参数补加参数
    @ApiModelProperty(value="Normal_Reserve",name="tx_date",example="")
    private String tx_date;

    //  Work order 根据传入参数补加参数
    @ApiModelProperty(value="Normal_Reserve",name="biz_date",example="")
    private String biz_date;


    @ApiModelProperty(value="Normal_Reserve",name="item_details",example="")
    private List<CreateDtlBean> item_details;

}
