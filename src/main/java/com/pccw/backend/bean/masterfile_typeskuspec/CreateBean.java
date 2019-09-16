package com.pccw.backend.bean.masterfile_typeskuspec;


import com.pccw.backend.bean.BaseBean;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
public class CreateBean extends BaseBean {

    private String typeId;

    private String skuId;

    private String specId;

    private String active;

    
}