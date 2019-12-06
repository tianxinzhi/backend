package com.pccw.backend.ctrl;


import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.ResultRecode;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_replenishment.CreateReplBean;
import com.pccw.backend.bean.stock_replenishment.CreateReplDtlBean;
import com.pccw.backend.bean.stock_replenishment.SearchBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/stock_replenishment")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Stock_ReplenishmentCtrl extends BaseCtrl<DbResLogRepl> {

    @Autowired
    ResLogReplRepository repo;
    @Autowired
    ResSkuRepoRepository rsRepo;
    @Autowired
    ResRepoRepository rRepo;
    @Autowired
    ResStockTypeRepository rstRepo;
    @Autowired
    ResSkuRepository resSkuRepo;
    @Autowired
    ResProcessRepository resProcess;
    @Autowired
    ResFlowRepository repoFlow;

    /**
     * 收货
     * @param b
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    @ApiOperation(value="创建replenishment",tags={"stock_replenishment"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public JsonResult create(@RequestBody CreateReplBean b) {
        try {
            long t = new Date().getTime();
            b.setCreateAt(t);
            b.setActive("Y");
            if(StaticVariable.LOGORDERNATURE_REPLENISHMENT_REQUEST.equals(b.getReplType())){
                b.setLogOrderNature(StaticVariable.LOGORDERNATURE_REPLENISHMENT_REQUEST);
                b.setStatus(StaticVariable.STATUS_WAITING);
            }else{
                if(!Objects.isNull(b.getLogRepoOut())){
                    b.setRepoIdFrom(b.getLogRepoOut());
                }
                b.setRepoIdTo(b.getLogRepoIn());
                b.setLogOrderNature(b.getReplType());
                b.setStatus(StaticVariable.STATUS_DONE);
            }
            b.setLogType(StaticVariable.LOGTYPE_REPL);

            DbResLogRepl repl = new DbResLogRepl();
            BeanUtils.copyProperties(b, repl);
            List<DbResLogReplDtl> dtlss = new ArrayList<DbResLogReplDtl>();
            repl.setLine(dtlss);
            List<CreateReplDtlBean> dtls = b.getLine();
            for(CreateReplDtlBean dt:dtls){
                dt.setCreateAt(t);
                dt.setLogTxtBum(b.getLogTxtBum());
                DbResLogReplDtl dtl = new DbResLogReplDtl();
                BeanUtils.copyProperties(dt, dtl);
                if(StaticVariable.LOGORDERNATURE_REPLENISHMENT_REQUEST.equals(b.getReplType())){
                    dtl.setLisStatus(StaticVariable.LISSTATUS_WAITING);
                }else{
                    dtl.setDtlAction(StaticVariable.DTLACTION_ADD);
                    dtl.setDtlSubin(StaticVariable.DTLSUBIN_GOOD);
                    dtl.setLisStatus(StaticVariable.LISSTATUS_WAITING);
                    dtl.setStatus(StaticVariable.STATUS_AVAILABLE);

                    //插入表res_sku_repo 添加或修改qty(工作流加入后 需要process的status为approve状态时再入库sku_repo)
                    /*DbResSkuRepo skuShop = rsRepo.findQtyByRepoAndShopAndType(b.getRepoIdTo(), dtl.getDtlSkuId(), 3l);
                    if(!Objects.isNull(skuShop)){
                        DbResSkuRepo skuShop1 = rsRepo.findById(skuShop.getId()).get();
                        skuShop1.setQty((int) (skuShop.getQty()+dtl.getDtlQty()));
                        skuShop1.setUpdateAt(t);
                    }else{
                        DbResSkuRepo skuShop2 = new DbResSkuRepo();
                        skuShop2.setCreateAt(t);
                        skuShop2.setUpdateAt(t);
                        skuShop2.setActive("Y");
                        skuShop2.setQty((int) dtl.getDtlQty());
                        skuShop2.setRepo(rRepo.findById(b.getRepoIdTo()).get());
                        skuShop2.setStockType(rstRepo.findById(3l).get());
                        skuShop2.setSku(resSkuRepo.findById(dtl.getDtlSkuId()).get());
                        rsRepo.saveAndFlush(skuShop2);
                    }*/
                }
                dtl.setDbResLogRepl(repl);
                repl.getLine().add(dtl);
            }

            //生成审批流数据
            DbResProcess process = new DbResProcess();
            process.setLogTxtBum(b.getLogTxtBum());
            process.setRepoId(b.getLogRepoIn());
            process.setStatus(StaticVariable.PROCESS_PENDING_STATUS);
            process.setRemark(b.getRemark());
            process.setActive("Y");
            process.setCreateAt(t);
            process.setUpdateAt(t);
            if(StaticVariable.LOGORDERNATURE_REPLENISHMENT_REQUEST.equals(b.getReplType())) {
                process.setLogOrderNature(StaticVariable.LOGORDERNATURE_REPLENISHMENT_REQUEST);
            }else {
                process.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_IN_FROM_WAREHOUSE);
            }
            //根据OrderNature查询flow,flow和nature一对一关系
            DbResFlow resFlow =repoFlow.findByFlowNature(process.getLogOrderNature());
            process.setFlowId(resFlow.getId());
            ArrayList<DbResProcessDtl> processDtls= new ArrayList<>();
            for(int i=0;i<resFlow.getResFlowStepList().size();i++) {
                DbResProcessDtl  resProcessDtl  =new DbResProcessDtl();
                resProcessDtl.setFlowId(resFlow.getId());
                resProcessDtl.setRemark(b.getRemark());
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

            repo.saveAndFlush(repl);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            System.out.println(e);
            return JsonResult.fail(e);
        }
    }

    @RequestMapping(method = RequestMethod.POST,path="/searchReplenishmentInfo")
    public JsonResult searchReplenishmentInfo(@RequestBody SearchBean bean){
        try {
            log.info(bean.toString());
            String batchId = Objects.isNull(bean.getLogBatchId()) ? "" : bean.getLogBatchId();
            String dnNum = Objects.isNull(bean.getLogDnNum()) ? "" : bean.getLogDnNum();
            List res = repo.getReplenishmentInfo(batchId, dnNum);
            List list = ResultRecode.returnHumpNameForList(res);
            return JsonResult.success(list);
        }catch (Exception e){
            return JsonResult.fail(e);
        }
    }
}
