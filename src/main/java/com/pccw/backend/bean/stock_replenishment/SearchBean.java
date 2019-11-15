package com.pccw.backend.bean.stock_replenishment;

import com.pccw.backend.bean.BaseSearchBean;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchBean extends BaseSearchBean {
    private String logBatchId;
    private String logDnNum;
}
