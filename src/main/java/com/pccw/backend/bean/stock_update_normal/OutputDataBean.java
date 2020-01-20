package com.pccw.backend.bean.stock_update_normal;


import com.pccw.backend.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
 @ApiModel(value="Stock update模块 - InputBean",description="")
public class OutputDataBean {

    private String tx_id;

    private List<OutputItemBean> item_details;

}