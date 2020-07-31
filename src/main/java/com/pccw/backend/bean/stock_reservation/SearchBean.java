package com.pccw.backend.bean.stock_reservation;

import com.pccw.backend.bean.BaseBean;
import com.pccw.backend.bean.BaseSearchBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="Stock_ReservationCtrl",description="")
public class SearchBean extends BaseSearchBean {

    @ApiModelProperty(value="customerType",name="customerType",example="")
    private String customerType;

    @ApiModelProperty(value="repoId",name="repoId",example="")
    private Long repoId;

    @ApiModelProperty(value="paymentStatus",name="paymentStatus",example="")
    private String paymentStatus;

    @ApiModelProperty(value="sku",name="sku",example="")
    private String sku;
}
