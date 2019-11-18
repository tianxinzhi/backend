package com.pccw.backend.bean.masterfile_spec;


import com.pccw.backend.bean.BaseBean;
import com.pccw.backend.entity.DbResSpecAttr;
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
 @ApiModel(value="Spec模块 - EditBean",description="")
public class SpecAttrEditBean extends DbResSpecAttr {

    private Long specId;

}