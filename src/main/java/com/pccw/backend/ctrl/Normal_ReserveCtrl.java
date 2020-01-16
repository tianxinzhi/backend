package com.pccw.backend.ctrl;


import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.nr_normal_reserve.CreateBean;
import com.pccw.backend.bean.nr_normal_reserve.CreateDtlBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.ResLogRorRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Slf4j
@RestController
@RequestMapping("/Normal_ReserveCtrl")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Normal_ReserveCtrl extends BaseCtrl<DbResLogRor> {

    @Autowired
    ResLogRorRepository rorRepository;

    @ApiOperation(value="创建Normal_ReserveCtrl",tags={"Normal_ReserveCtrl"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody CreateBean b){
        try {
            //处理头表数据
            long time = new Date().getTime();
            DbResLogRor dbResLogRor = new DbResLogRor(null,b.getOrder_system(),b.getOrder_id(),null,"N",b.getSales_id(),b.getTx_date(),b.getBiz_date(),null);
            dbResLogRor.setActive("Y");
            dbResLogRor.setUpdateAt(time);
            dbResLogRor.setCreateAt(time);
            dbResLogRor.setCreateBy(getAccount());
            dbResLogRor.setUpdateBy(getAccount());
//            dbResLogRor.setCcc();
//            dbResLogRor.setWo();
            dbResLogRor.setLogOrderNature(b.getRequest_nature());
            dbResLogRor.setLogType("o");
//            dbResLogRor.setStatus();
            dbResLogRor.setRemark(b.getRemarks());
            //处理行表数据
            List<DbResLogRorDtl> logRorDtlList = new ArrayList<>();
            List<CreateDtlBean> item_details = b.getItem_details();
            if(Objects.nonNull(item_details) && item_details.size() > 0){
                item_details.forEach(d->{
                    DbResLogRorDtl dbResLogRorDtl = new DbResLogRorDtl(null,d.getSku_id(),d.getItem_id(),d.getRepo_id(),d.getQuantity(),d.getCcc(),d.getWo(),dbResLogRor);
                    dbResLogRorDtl.setActive("Y");
                    dbResLogRorDtl.setCreateAt(time);
                    dbResLogRorDtl.setCreateBy(getAccount());
                    dbResLogRorDtl.setUpdateAt(time);
                    dbResLogRorDtl.setUpdateBy(getAccount());
                    if("NRS".equals(b.getRequest_nature())){
                        dbResLogRorDtl.setDtlAction("A");
                        dbResLogRorDtl.setStatus("NRE");
                    }else if("CNRS".equals(b.getRequest_nature())){
                        dbResLogRorDtl.setDtlAction("A");
                        dbResLogRorDtl.setStatus("AVL");
                    }else {
                        dbResLogRorDtl.setDtlAction("D");
                        dbResLogRorDtl.setStatus("NRE");
                        dbResLogRorDtl.setLisStatus("Good");
                    }
                    logRorDtlList.add(dbResLogRorDtl);
                    if(!"NPU".equals(b.getRequest_nature())){
                        DbResLogRorDtl dbResLogRorDtl1 = new DbResLogRorDtl();
                        BeanUtils.copyProperties(dbResLogRorDtl,dbResLogRorDtl1);
                        if("NRS".equals(b.getRequest_nature())) {
                            dbResLogRorDtl1.setDtlAction("D");
                            dbResLogRorDtl1.setStatus("AVL");
                        }else if("CNRS".equals(b.getRequest_nature())){
                            dbResLogRorDtl1.setDtlAction("D");
                            dbResLogRorDtl1.setStatus("NRE");
                        }
                        logRorDtlList.add(dbResLogRorDtl1);
                    }
                });
           }
            dbResLogRor.setLine(logRorDtlList);
            rorRepository.saveAndFlush(dbResLogRor);
            /*//创建工作流对象
            DbResProcess process = new DbResProcess();
            process.setLogTxtBum(b.getLogTxtBum());
            process.setRepoId(b.getLogRepoOut());
            process.setRemark(b.getRemark());
            process.setCreateAt(t);
            process.setUpdateAt(t);
            process.setLogOrderNature(b.getLogOrderNature());
            //生成工作流数据
            processProcessCtrl.joinToProcess(process);*/
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }
}