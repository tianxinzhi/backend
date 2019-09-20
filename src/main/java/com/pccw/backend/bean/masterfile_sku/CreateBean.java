package com.pccw.backend.bean.masterfile_sku;

import com.pccw.backend.bean.BaseBean;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBean extends BaseBean {

    private String classId;
    private String skuCode;
    private String skuDesc;
    private String skuName;

}
