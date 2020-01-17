package com.pccw.backend.bean.stock_update_normal;


import com.pccw.backend.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
 @AllArgsConstructor
 @ApiModel(value="Stock update模块 - InputBean",description="")
public class OutputItemBean {

    private String detail_id;

    private String sku_id;

    private String quantity;

    private String item_id;

    private String repo_id;

    private String ccc;

    private String wo;


}