package com.pccw.backend.bean.process_process;

import com.pccw.backend.entity.DbResProcessDtl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: ChenShuCheng
 * @create: 2019-12-05 18:09
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtlRecodeBean extends DbResProcessDtl {

    private String roleName;

    private Step step;

}
