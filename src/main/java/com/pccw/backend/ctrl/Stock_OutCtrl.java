package com.pccw.backend.ctrl;


import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.LabelAndValue;
import com.pccw.backend.bean.ResultRecode;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_out.CreateBean;
import com.pccw.backend.bean.stock_out.SearchBean;
import com.pccw.backend.bean.stock_out.ProcessCreateBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/stock_out")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Stock_OutCtrl  extends BaseCtrl<DbResLogMgt> {

    @Autowired
    private ResLogMgtRepository repo;

    @Autowired
    ResSkuRepoRepository skuRepoRepository;

    @Autowired
    ResRepoRepository repoRepository;

    @Autowired
    ResProcessRepository resProcess;

    @Autowired
    ResFlowRepository repoFlow;

    @ApiOperation(value="查询shop",tags={"masterfile_repo"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        try {
            List<Map<String, Object>> list = skuRepoRepository.findByTypeIdAndRepoId(b.getFromRepoId(),b.getToRepoId());
            List<Map<String, Object>> humpNameForList = ResultRecode.returnHumpNameForList(list);
            return JsonResult.success(humpNameForList);

        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="创建stock_out",tags={"stock_out"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody CreateBean b){
        try {
            long t = new Date().getTime();
            b.setCreateAt(t);
            b.setActive("Y");
            b.setStatus(StaticVariable.STATUS_WAITING);
            for(int i=0;i<b.getLine().size();i++) {
                b.getLine().get(i).setUpdateAt(t);
                b.getLine().get(i).setCreateAt(t);
                b.getLine().get(i).setActive("Y");
                b.getLine().get(i).setLogTxtBum(b.getLogTxtBum());
                if(b.getLine().get(i).getDtlAction().equals("D")){
                    b.getLine().get(i).setDtlRepoId(b.getLogRepoOut());
                }else {
                    b.getLine().get(i).setDtlRepoId(b.getLogRepoIn());
                    int res = repoRepository.getRepoType(b.getLogRepoIn());
                    if(res>0) {
                        b.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_OUT_STW);
                    }else {
                        b.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_OUT_STS);
                    }
                }
            }
            //生成审批流数据
            DbResProcess process = new DbResProcess();
            process.setLogTxtBum(b.getLogTxtBum());
            process.setRepoId(b.getLogRepoOut());
            process.setStatus(StaticVariable.PROCESS_PENDING_STATUS);
            process.setRemark(b.getRemark());
            process.setActive("Y");
            process.setCreateAt(t);
            process.setUpdateAt(t);
            if(b.getLogOrderNature().equals(StaticVariable.LOGORDERNATURE_STOCK_OUT_STW)) {
                process.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_OUT_STW);
            }else {
                process.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_OUT_STS);
            }
            //根据OrderNature查询flow,flow和nature一对一关系
            DbResFlow resFlow =repoFlow.findByFlowNature(process.getLogOrderNature());
            process.setFlowId(resFlow.getId());
            ArrayList<DbResProcessDtl> processDtls= new ArrayList<>();
            for(int i=0;i<resFlow.getResFlowStepList().size();i++) {
                DbResProcessDtl  resProcessDtl  =new DbResProcessDtl();
                resProcessDtl.setFlowId(resFlow.getId());
                resProcessDtl.setStepId(resFlow.getResFlowStepList().get(i).getId());
                resProcessDtl.setStepNum(resFlow.getResFlowStepList().get(i).getStepNum());
                resProcessDtl.setRoleId(resFlow.getResFlowStepList().get(i).getRoleId());
                //审批流初始化数据StepNum1默认PENDING,其他StepNum默认WAITING
                if(resFlow.getResFlowStepList().get(i).getStepNum().equals("1")) {
                    resProcessDtl.setStatus(StaticVariable.PROCESS_PENDING_STATUS);
                }else {
                    resProcessDtl.setStatus(StaticVariable.PROCESS_WAITING_STATUS);
                }
                processDtls.add(resProcessDtl);
            }
            process.setProcessDtls(processDtls);
            resProcess.saveAndFlush(process);

            //审批流完成后更新sku_repo
           // UpdateSkuRepoQty(b);
            //
            return this.create(repo, DbResLogMgt.class, b);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }


    //审批流完成后更新sku_repo
    public void UpdateSkuRepoQty(String logTxtNum) {

//        for(int i=0;i<b.getLine().size();i++) {
//            if (b.getLine().get(i).getDtlAction().equals("D")) {
//                System.out.println(-b.getLine().get(i).getDtlQty());
//                int res = skuRepoRepository.updateQtyByRepoAndShopAndTypeAndQty(b.getLogRepoOut(),b.getLine().get(i).getDtlSkuId(),3,-b.getLine().get(i).getDtlQty());
//                //if(res<=0) return JsonResult.fail(new Exception());
//            }
//        }
    }


}