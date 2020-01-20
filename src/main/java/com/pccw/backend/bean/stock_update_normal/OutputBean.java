package com.pccw.backend.bean.stock_update_normal;


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
 @ApiModel(value="Stock update模块 - InputBean",description="")
public class OutputBean  {

    private String state;

    private String code;

    private String msg;

    private OutputDataBean data;

}