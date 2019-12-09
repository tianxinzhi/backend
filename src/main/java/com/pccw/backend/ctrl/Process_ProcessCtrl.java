package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.process_process.*;
import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.entity.DbResProcess;
import com.pccw.backend.repository.ResLogMgtRepository;
import com.pccw.backend.repository.ResProcessRepository;
import com.pccw.backend.repository.ResRoleRepository;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
            return this.edit(processRepository, DbResProcess.class, b);
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
            List<RecodeBean> list = getRecodes(res);
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
            List<RecodeBean> list = getRecodes(res);
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

            List<RecodeBean> list = getRecodes(res);

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
    private List<RecodeBean> getRecodes(List<DbResProcess> res) {
        return res.stream().map(r -> {
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

            return recodeBean;
        }).collect(Collectors.toList());
    }
}
