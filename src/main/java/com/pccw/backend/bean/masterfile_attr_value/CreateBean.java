package com.pccw.backend.bean.masterfile_attr_value;

import com.pccw.backend.bean.BaseBean;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBean extends BaseBean {

    private Long attrId;
    private String attrValue;
    private String unitOfMeasure;
    private String valueFrom;
    private String valueTo;
    private String active;
}
