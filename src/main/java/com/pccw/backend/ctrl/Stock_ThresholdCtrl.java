package com.pccw.backend.ctrl;


import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_category.CategoryLogMgtBean;
import com.pccw.backend.bean.stock_threshold.SearchBean;
import com.pccw.backend.bean.stock_threshold.ThresholdLogMgtBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.Session;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Slf4j
@RestController
@RequestMapping("/stock_threshold")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Stock_ThresholdCtrl extends BaseCtrl<DbResLogMgt> {

    @Autowired
    private ResLogMgtRepository resLogMgtRepository;
    @Autowired
    Process_ProcessCtrl processProcessCtrl;

    @ApiOperation(value="创建stock_threshold",tags={"stock_threshold"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody ThresholdLogMgtBean b){
        try {
            long t = new Date().getTime();
            if(Objects.nonNull(b.getLine()) && b.getLine().size() > 0){
                b.getLine().forEach(l->{
                    l.setCreateAt(t);
                    l.setCreateBy(getAccount());
                    l.setUpdateAt(t);
                    l.setUpdateBy(getAccount());
                    l.setActive("Y");
                });
            }
            JsonResult jsonResult = this.create(resLogMgtRepository, DbResLogMgt.class, b);
            //创建工作流对象
//            if(jsonResult.getCode().equals("000")) {
//                DbResProcess process = new DbResProcess();
//                process.setLogTxtBum(b.getLogTxtBum());
//                process.setRepoId(b.getLogRepoOut());
//                process.setRemark(b.getRemark());
//                process.setCreateAt(t);
//                process.setUpdateAt(t);
//                process.setLogOrderNature(b.getLogOrderNature());
//                //生成工作流数据
//                processProcessCtrl.joinToProcess(process);
//            }
            return jsonResult;
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="搜索threshold",tags={"stock_threshold"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public JsonResult search(@RequestBody SearchBean b) {
        try {
            String repoId = Objects.isNull(b.getRepoId()) ? "" : b.getRepoId();
            String skuId = Objects.isNull(b.getSkuId()) ? "" : b.getSkuId();
            List<Map> list = resLogMgtRepository.getStockThreshold(repoId,skuId);
            return JsonResult.success(list);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }
}