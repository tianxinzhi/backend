package com.pccw.backend.bean.process_process;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@ApiModel(value = "Process 模块 - SearchBean", description = "")
public class SearchBean extends BaseSearchBean {

    @PredicateAnnotation(type = PredicateType.BETWEEN)
    @ApiModelProperty(value="时间区间",name="date",example="")
    private Date[] date;

    @ApiModelProperty(value="业务类型编码",name="logOrderNature",example="")
    private String logOrderNature;

    @ApiModelProperty(value="商店id",name="repoId",example="")
    private Long repoId;

    @PredicateAnnotation(type = PredicateType.LIKE)
    @ApiModelProperty(value="Txtnumber",name="logTxtBum",example="")
    private String logTxtBum;
}