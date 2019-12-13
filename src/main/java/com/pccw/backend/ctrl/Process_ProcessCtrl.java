package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.process_process.*;
import com.pccw.backend.entity.DbResFlow;
import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.entity.DbResProcess;
import com.pccw.backend.entity.DbResProcessDtl;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.Convertor;
import com.pccw.backend.util.Session;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/process")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Process_ProcessCtrl extends BaseCtrl{

    @Autowired
    ResProcessRepository processRepository;

    @Autowired
    ResRoleRepository roleRepository;

    @Autowired
    ResLogMgtRepository logMgtRepository;

    @Autowired
    ResAccountRepository accountRepository;

    @Autowired
    Session<Map> session;

    @Autowired
    ResFlowRepository repoFlow;

    @Autowired
    Stock_OutCtrl  outCtrl;

    @Autowired
    Stock_InCtrl inCtrl;

    @ApiOperation(value="process",tags={"process"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult edit(@RequestBody EditBean b){
        try{
            log.info(b.toString());
            long t = new Date().getTime();

            Optional<DbResProcess> optional = processRepository.findById(b.getId());
            DbResProcess dbResProcess = optional.get();
            BeanUtils.copyProperties(dbResProcess,b);
            b.setUpdateAt(t);
            //b.setUpdateBy();
            b.setStatus(b.getStatusPro());
            for(int i=0;i<b.getSteps().size();i++) {
                for(int j=0;j<b.getProcessDtls().size();j++) {
                    if (b.getProcessDtls().get(j).getId().equals(b.getSteps().get(i).getProcessDtlsId()) ) {
                          b.getProcessDtls().get(j).setUpdateAt(t);
                          b.getProcessDtls().get(j).setActive("Y");
                          b.getProcessDtls().get(j).setRemark(b.getSteps().get(i).getRemark());
                          b.getProcessDtls().get(j).setStatus(b.getSteps().get(i).getTitle());
                    }
                }
            }

            JsonResult result = this.edit(processRepository, DbResProcess.class, b);

            //审批流程修改成功，且最后一步审批通过，将log信息存入skuRepo表
            if(b.getStatusPro().equals(StaticVariable.PROCESS_APPROVED_STATUS) && result.getCode().equals("000")){
                //根据LogOrderNature判断从哪个ctrl更新数据
                if(b.getLogOrderNature().equals(StaticVariable.LOGORDERNATURE_STOCK_OUT_STS)||b.getLogOrderNature().equals(StaticVariable.LOGORDERNATURE_STOCK_OUT_STW)){
                    outCtrl.UpdateSkuRepoQty(b.getLogTxtBum());
                }else if(b.getLogOrderNature().equals(StaticVariable.LOGORDERNATURE_STOCK_IN_STS)||b.getLogOrderNature().equals(StaticVariable.LOGORDERNATURE_STOCK_IN_WITHOUT_PO_STW)){
                    inCtrl.UpdateSkuRepoQty(b.getLogTxtBum());
                }else if(b.getLogOrderNature().equals(StaticVariable.LOGORDERNATURE_STOCK_IN_FROM_WAREHOUSE)){
                    inCtrl.UpdateSkuRepoQty(b.getLogTxtBum());
                } else if(b.getLogOrderNature().equals(StaticVariable.LOGORDERNATURE_STOCK_TAKE_ADJUSTMENT)){
                    inCtrl.UpdateSkuRepoQty(b.getLogTxtBum());
                }else if(b.getLogOrderNature().equals(StaticVariable.LOGORDERNATURE_REPLENISHMENT_REQUEST)){
                    inCtrl.UpdateSkuRepoQty(b.getLogTxtBum());
                }
            }

            return result;
        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }

    }


    @ApiOperation(value = "搜索recode",tags = "Process",notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST,path = "/search")
    public JsonResult search(@RequestBody SearchBean bean){
        try {
            List<DbResProcess> res = getDbResProcesses(bean);
            Map user = session.getUser();
            String roles = user.get("role").toString();
            List<RecodeBean> list = getRecodes(res,roles);
            return JsonResult.success(list);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value = "搜索my request",tags = "Process",notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST,path = "/myReqSearch")
    public JsonResult myReqSearch(@RequestBody ReqOrPedSearchBean bean){
        try {
            List<DbResProcess> res = getDbResProcesses(bean);
            Map user = session.getUser();
            String roles = user.get("role").toString();
            List<RecodeBean> list = getRecodes(res,roles);
            return JsonResult.success(list);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value = "搜索pending for me",tags = "Process",notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST,path = "/myPendingSearch")
    public JsonResult myPendingSearch(@RequestBody ReqOrPedSearchBean bean){
        try {
            timeRangeHandle(bean);

            String nature = bean.getLogOrderNature() == null ? "" : bean.getLogOrderNature();
            String repoId = bean.getRepoId() == null ? "" : String.valueOf(bean.getRepoId());
            String txtNum = bean.getLogTxtBum() == null ? "" : bean.getLogTxtBum();

            List<Long> ids = processRepository.findIdsByPending(bean.getCreateBy(),nature,repoId,txtNum,bean.getCreateAt()[0],bean.getCreateAt()[1]);

            List<DbResProcess> res = processRepository.findDbResProcessesByIdIn(ids);

            Map user = session.getUser();
            String roles = user.get("role").toString();
            List<RecodeBean> list = getRecodes(res,roles);

            return JsonResult.success(list);
        } catch (Exception e) {
            log.info(e.toString());
            return JsonResult.fail(e);
        }
    }

    /**
     * 通过查询条件查询Process和Process明细数据
     * @param bean
     * @return
     * @throws IllegalAccessException
     */
    private List<DbResProcess> getDbResProcesses(@RequestBody SearchBean bean) throws IllegalAccessException {
        timeRangeHandle(bean);
        log.info(bean.toString());

        return processRepository.findAll(Convertor.convertSpecification(bean));
    }

    /**
     * 处理获取的时间范围字段
     * @param bean
     */
    private void timeRangeHandle(@RequestBody SearchBean bean) {
        Date[] dateRange = Objects.nonNull(bean.getDate()) && bean.getDate().length>0 ? bean.getDate() : new Date[2];

        dateRange[0] = Convertor.beginOfDay(dateRange[0]);
        dateRange[1] = Convertor.endOfDay(dateRange[1]);

        Long[] timeRange = {Objects.nonNull(dateRange[0]) ? dateRange[0].getTime() : 946656000000L,Objects.nonNull(dateRange[1]) ? dateRange[1].getTime() : 4102416000000L};
        bean.setCreateAt(timeRange);
    }

    /**
     * 将字段转为对应的step状态
     * @param str
     * @return
     */
    private String getStepActive(String str) {
        String status = "";

        switch (str){
            case StaticVariable.PROCESS_PENDING_STATUS:
                status = "process";
                break;
            case StaticVariable.PROCESS_APPROVED_STATUS:
                status = "finish";
                break;
            case StaticVariable.PROCESS_WAITING_STATUS:
                status = "wait";
                break;
            case StaticVariable.PROCESS_REJECTED_STATUS:
                status = "error";
                break;
        }

        return status;
    }

    /**
     * 封装返回页面的数据格式
     * @param res
     * @return
     */
    private List<RecodeBean> getRecodes(List<DbResProcess> res,String roles) {
        return res.stream().map(r -> {
            //判断操作人是否具有当前审批权限
            List<DbResProcessDtl> collect = r.getProcessDtls().stream().filter(item ->
                    //判断当前状态为pending，且当前行的roleId存在于操作人的roleId中
                    item.getStatus().equals(StaticVariable.PROCESS_PENDING_STATUS) && roles.contains(item.getRoleId().toString())
            ).collect(Collectors.toList());
            boolean checkable = collect.size() > 0 ;
            //log表信息
            DbResLogMgt logDtls = logMgtRepository.findDbResLogMgtByLogTxtBum(r.getLogTxtBum());
            //获取rolename 封装step数据
            List<Step> stepList = r.getProcessDtls().stream().map(item -> {
                String roleName = roleRepository.findById(item.getRoleId()).get().getRoleName();
                return new Step(item.getStatus(), roleName, getStepActive(item.getStatus()), item.getStepNum(),item.getId(),item.getRemark());
            }).collect(Collectors.toList());
            //封装返回页面数据
            RecodeBean recodeBean = new RecodeBean();
            BeanUtils.copyProperties(r,recodeBean);
            recodeBean.setLogDtls(logDtls);
            recodeBean.setSteps(stepList);
            recodeBean.setCheckable(checkable);
            return recodeBean;
        }).collect(Collectors.toList());
    }

    /**
     *生成工作流数据
     * @param process
     */
    public void joinToProcess(DbResProcess process) {
        process.setStatus(StaticVariable.PROCESS_PENDING_STATUS);
        Map user = session.getUser();
        process.setCreateBy(Long.parseLong(user.get("account").toString()));
        process.setActive("Y");
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
        processRepository.saveAndFlush(process);
    }


}
