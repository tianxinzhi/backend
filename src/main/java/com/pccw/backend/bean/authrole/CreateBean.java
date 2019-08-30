package com.pccw.backend.bean.authrole;


import com.pccw.backend.bean.BaseBean;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SearchCondition
 */

@Data
@NoArgsConstructor
public class CreateBean extends BaseBean{

    private String roleName;

    private String roleDesc;
    
}