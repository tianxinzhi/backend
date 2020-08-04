package com.pccw.backend.bean.reservation_rule;

import com.pccw.backend.bean.BaseSearchBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="Stock_ReservationCtrl",description="")
public class SearchBean extends BaseSearchBean {

    @ApiModelProperty(value="sku",name="sku",example="")
    private String sku;
}
