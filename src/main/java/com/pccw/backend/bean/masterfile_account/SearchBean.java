package com.pccw.backend.bean.masterfile_account;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "User 模块 - SearchBean", description = "")
public class SearchBean extends BaseSearchBean {

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="角色",name="roleId",example="")
    private String roleId;

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="账号",name="accountName",example="")
    private String accountName;

}
