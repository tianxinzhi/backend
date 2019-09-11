package com.pccw.backend.bean.res_sku;

import com.pccw.backend.bean.BaseBean;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CreateBean extends BaseBean {

    private String classId;
    private String skuCode;
    private String skuDesc;
    private String skuName;
    private Date createAt;
    private String createBy;
    private String status;
    private Date updateAt;
    private String updateBy;

}
