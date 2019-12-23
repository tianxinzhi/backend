package com.pccw.backend.bean.auth_right;

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
@ApiModel(value="Right模块 - CreateBean",description="")
public class CreateBean extends BaseBean{

    @ApiModelProperty(value="权限名称",name="rightName",example="delete")
    private String rightName;
    
    @ApiModelProperty(value="权限路径",name="rightUrl",example="/auth_right/search")
    private String rightUrl;
    
    @ApiModelProperty(value="权限PID",name="rightPid",example="45")
    private Long rightPid;

    @ApiModelProperty(value="权限类型",name="rightType",example="Button")
    private String rightType;
    
}