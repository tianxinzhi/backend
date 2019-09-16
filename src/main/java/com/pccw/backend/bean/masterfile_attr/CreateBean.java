package com.pccw.backend.bean.masterfile_attr;

import com.pccw.backend.bean.BaseBean;
import com.pccw.backend.entity.DbResAttrValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateBean extends BaseBean {

    private String attrName;
    private String attrDesc;
    private String attrValueType;
    private String active;
    private List<DbResAttrValue> attrValues;
}
