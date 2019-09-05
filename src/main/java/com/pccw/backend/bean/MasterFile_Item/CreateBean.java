package com.pccw.backend.bean.MasterFile_Item;

import com.pccw.backend.bean.BaseBean;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBean extends BaseBean {
    private Long id;

    private String itemCode;

    private String itemName;
}
