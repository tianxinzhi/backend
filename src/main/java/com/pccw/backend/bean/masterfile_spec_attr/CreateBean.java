package com.pccw.backend.bean.masterfile_spec_attr;

import com.pccw.backend.bean.BaseBean;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBean extends BaseBean {

    private Long specId;
    private String verId;
    private Long attrId;
    private Long attrValueId;

}
