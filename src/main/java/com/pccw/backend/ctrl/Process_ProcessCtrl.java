package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.process_process.*;
import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.entity.DbResProcess;
import com.pccw.backend.entity.DbResProcessDtl;
import com.pccw.backend.entity.DbResRole;
import com.pccw.backend.repository.ResLogMgtRepository;
import com.pccw.backend.repository.ResProcessRepository;
import com.pccw.backend.repository.ResRoleRepository;
import com.pccw.backend.util.Convertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

    @RequestMapping(method = RequestMethod.POST,path = "/myReqSearch")
    public JsonResult myReqSearch(@RequestBody ReqOrPedSearchBean bean){
        try {
            List<DbResProcess> res = getDbResProcesses(bean);
            List<RecodeBean> list = getRecodes(res);
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

//    @RequestMapping(method = RequestMethod.POST,path = "/myPendingSearch")
//    public JsonResult myPendingSearch(@RequestBody ReqOrPedSearchBean bean){
//        try {
//            List<DbResProcess> res = getDbResProcesses(bean);
//
//            return JsonResult.success(res);
//        } catch (Exception e) {
//            return JsonResult.fail(e);
//        }
//    }

    private List<DbResProcess> getDbResProcesses(@RequestBody SearchBean bean) throws IllegalAccessException {
        Date[] dateRange = Objects.nonNull(bean.getDate()) && bean.getDate().length>0 ? bean.getDate() : new Date[2];

        dateRange[0] = Convertor.beginOfDay(dateRange[0]);
        dateRange[1] = Convertor.endOfDay(dateRange[1]);

        Long[] timeRange = {Objects.nonNull(dateRange[0]) ? dateRange[0].getTime() : 946656000000L,Objects.nonNull(dateRange[1]) ? dateRange[1].getTime() : 4102416000000L};
        bean.setCreateAt(timeRange);
        log.info(bean.toString());

        return processRepository.findAll(Convertor.convertSpecification(bean));
    }

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

    private List<RecodeBean> getRecodes(List<DbResProcess> res) {
        return res.stream().map(r -> {

            List<DbResLogMgt> logDtls = logMgtRepository.findDbResLogMgtByLogTxtBum(r.getLogTxtBum());

            List<DtlRecodeBean> processDtls = r.getProcessDtls().stream().map(item -> {
                String roleName = roleRepository.findById(item.getRoleId()).get().getRoleName();
                Step step = new Step(item.getStatus(), roleName, getStepActive(item.getStatus()));
                DtlRecodeBean dtlRecodeBean = new DtlRecodeBean(roleName, step);
                BeanUtils.copyProperties(item, dtlRecodeBean);
                return dtlRecodeBean;
            }).collect(Collectors.toList());

            List<Step> steps = processDtls.stream().map(item -> {
                return item.getStep();
            }).collect(Collectors.toList());

            return new RecodeBean(processDtls, logDtls, steps);
        }).collect(Collectors.toList());
    }
}
