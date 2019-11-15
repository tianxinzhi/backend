package com.pccw.backend.bean.workflow_flow;


import com.pccw.backend.entity.DbResFlowStep;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
 @ApiModel(value="Spec模块 - CreateBean",description="")
public class FlowStepEditBean extends DbResFlowStep {

    private Long flowId;

}