package com.pccw.backend.bean.masterfile_spec;




import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
 @ApiModel(value="Spec模块 - EditBean",description="")
public class EditBean extends CreateBean {

    private Long id;
    
}