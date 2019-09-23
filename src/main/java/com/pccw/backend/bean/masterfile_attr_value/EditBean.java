package com.pccw.backend.bean.masterfile_attr_value;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="Attr_Value模块 - EditBean",description="")
public class EditBean extends CreateBean {

    private Long id;
}
