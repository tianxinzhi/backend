package com.pccw.backend.bean.stock_in;

import com.pccw.backend.bean.BaseBean;
import com.pccw.backend.entity.DbResLogMgtDtl;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value="StockIn - CreateBean",description="")
public class CreateBean extends BaseBean {
    private long logRepoIn;

    private long logRepoOut;

    private List<DbResLogMgtDtl> line;
}
