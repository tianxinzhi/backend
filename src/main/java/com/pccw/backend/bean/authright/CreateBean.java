package com.pccw.backend.bean.authright;


import com.pccw.backend.bean.BaseBean;



import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
public class CreateBean extends BaseBean{

    private String rightName;

    private String rightUrl;

    private String rightModule;
    
}