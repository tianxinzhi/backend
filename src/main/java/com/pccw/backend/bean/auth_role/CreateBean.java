package com.pccw.backend.bean.auth_role;


import com.pccw.backend.bean.BaseBean;


import com.pccw.backend.entity.DbResRoleRight;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * SearchCondition
 */

@Data
@NoArgsConstructor
public class CreateBean extends BaseBean{

    private String roleName;

    private String roleDesc;

    private String[] rightId;

//    private List<DbResRoleRight> resRoleRightList;

}