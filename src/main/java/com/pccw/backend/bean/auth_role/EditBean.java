package com.pccw.backend.bean.auth_role;




import com.pccw.backend.bean.BaseBean;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
public class EditBean extends CreateBean/*BaseBean*/ {

    private Long id;

  /*  private String roleName;

    private String roleDesc;

    private List<RoleRightEditBean> resRoleRightList;*/
}