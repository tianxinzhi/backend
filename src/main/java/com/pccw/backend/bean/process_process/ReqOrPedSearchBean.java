package com.pccw.backend.bean.process_process;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 查询自己申请或需要自己审批的bean
 * @author ChenShuCheng
 * @create: 2019-12-05 11:00
 **/

@Data
@NoArgsConstructor
@ApiModel(value = "Process 模块 - MyRequestSearchBean", description = "")
public class ReqOrPedSearchBean extends SearchBean{
    @PredicateAnnotation(type = PredicateType.EQUEL)
    @ApiModelProperty(value="操作人的id",name="createBy",example="")
    private long createBy;
}
