package com.pccw.backend.bean.masterfile_type;


import com.pccw.backend.bean.BaseBean;
import com.pccw.backend.entity.DbResClass;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
public class CreateBean extends BaseBean{

    private String active;

    private String typeName;

    private String typeCode;

    private String sequential;

    private String typeDesc;

    private Long classId;

    private List<DbResClass> classList;
}