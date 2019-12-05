package com.pccw.backend.bean.process_process;

import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.entity.DbResProcess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: ChenShuCheng
 * @create: 2019-12-05 19:20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecodeBean extends DbResProcess{

    private DbResLogMgt logDtls;

    private Steps steps;
}
