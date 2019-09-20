package com.pccw.backend.bean.user;

import com.pccw.backend.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "User 模块 - LoginBean", description = "")
public class LoginBean extends BaseBean {

    @ApiModelProperty(value="用户名",name="accountName",example="")
    private String accountName;

    @ApiModelProperty(value="密码",name="password",example="")
    private String password;
}
