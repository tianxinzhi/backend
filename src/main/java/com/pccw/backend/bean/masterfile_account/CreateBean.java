package com.pccw.backend.bean.masterfile_account;

import com.pccw.backend.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="Account模块 - CreateBean",description="")
public class CreateBean extends BaseBean {

    @ApiModelProperty(value="角色",name="roleId",example="delete")
    private Long roleId;

    @ApiModelProperty(value="账户",name="accountName",example="delete")
    private String accountName;

    @ApiModelProperty(value="密码",name="password",example="delete")
    private String password;

}
