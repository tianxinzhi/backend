package com.pccw.backend.bean.MasterFile_Class;


import com.pccw.backend.bean.BaseBean;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
public class CreateBean extends BaseBean{

    private String className;

    private String classDesc;
    
}