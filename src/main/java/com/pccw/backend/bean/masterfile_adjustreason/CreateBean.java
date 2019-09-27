package com.pccw.backend.bean.masterfile_adjustreason;

import com.pccw.backend.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="Adjust_Reason模块 - CreateBean",description="")
public class CreateBean extends BaseBean {

    @ApiModelProperty(value="调整名",name="adjustReasonName",example="")
    private String adjustReasonName;

}
