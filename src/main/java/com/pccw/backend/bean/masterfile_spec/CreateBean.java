package com.pccw.backend.bean.masterfile_spec;


import com.pccw.backend.bean.BaseBean;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
public class CreateBean extends BaseBean {

    private String specName;

    private String specDesc;

    private String verId;

    private String active;

    
}