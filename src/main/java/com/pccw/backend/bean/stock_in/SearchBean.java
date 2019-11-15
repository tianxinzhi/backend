package com.pccw.backend.bean.stock_in;

import com.pccw.backend.bean.BaseSearchBean;
import com.pccw.backend.entity.DbResLogMgtDtl;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SearchBean extends BaseSearchBean {
    private String logTxtNum;

//    private long logRepoOut;

//    private List<DbResLogMgtDtl> line;
}
