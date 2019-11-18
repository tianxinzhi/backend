package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.workflow_flow.*;
import com.pccw.backend.entity.DbResFlow;
import com.pccw.backend.entity.DbResRole;
import com.pccw.backend.repository.ResFlowRepository;
import com.pccw.backend.bean.BaseDeleteBean;


import com.pccw.backend.repository.ResRoleRepository;
import com.pccw.backend.util.Convertor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/workflow_flow")
public class WorkFlow_FlowCtrl extends BaseCtrl<DbResFlow>{

    @Autowired
    ResFlowRepository repo;

    @Autowired
    ResRoleRepository repoRole;

    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody SearchBean b) {

        try {
            Specification specification = Convertor.convertSpecification(b);
            List<DbResFlow> res =repo.findAll(specification, PageRequest.of(b.getPageIndex(),b.getPageSize())).getContent();
            ArrayList<com.pccw.backend.bean.workflow_flow.SearchBean> dbResFlow = new ArrayList<>();

            if(res != null && res.size() > 0){
                for (DbResFlow flow:res){
                    SearchBean searchBean = new SearchBean();
                    BeanUtils.copyProperties(flow, searchBean);
                    List<Map> stepList = new ArrayList<>();
                    for(int i=0;i<flow.getResFlowStepList().size();i++) {
                        Optional<DbResRole> optional = repoRole.findById(flow.getResFlowStepList().get(i).getRoleId());
                        DbResRole resRole = optional.get();
                        //详情
                        HashMap<Object, Object> hm = new HashMap<>();
                        hm.put("roleName",resRole.getRoleName());
                        hm.put("stepNum",flow.getResFlowStepList().get(i).getStepNum());
                        stepList.add(hm);
                    }
                    searchBean.setStepData(stepList);
                    dbResFlow.add(searchBean);
                }
            }
            return JsonResult.success(dbResFlow);
        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }

    }

    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
        return this.delete(repo,ids);
    }

    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody CreateBean b){
       try{
            long t = new Date().getTime();
            b.setUpdateAt(t);
            b.setCreateAt(t);
            for(int i=0;i<b.getResFlowStepList().size();i++) {
                b.getResFlowStepList().get(i).setUpdateAt(t);
                b.getResFlowStepList().get(i).setCreateAt(t);
                b.getResFlowStepList().get(i).setActive("Y");
            }
            return this.create(repo, DbResFlow.class, b);
       } catch (Exception e) {
           log.info(e.getMessage());
           return JsonResult.fail(e);
         }
    }

    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult edit(@RequestBody EditBean b){
        try{
            long t = new Date().getTime();
            Optional<DbResFlow> optional = repo.findById(b.getId());
            DbResFlow dbResFlow =optional.get();
            b.setUpdateAt(t);
            b.setCreateAt(dbResFlow.getCreateAt());
            for(int i=0;i<b.getResFlowStepList().size();i++) {
                for(int j=0;j<dbResFlow.getResFlowStepList().size();j++) {
                    if (dbResFlow.getResFlowStepList().get(j).getId()==b.getResFlowStepList().get(i).getId()) {
                        b.getResFlowStepList().get(i).setUpdateAt(t);
                        b.getResFlowStepList().get(i).setCreateAt(dbResFlow.getResFlowStepList().get(j).getCreateAt());
                        b.getResFlowStepList().get(i).setActive("Y");
                    }else if(Objects.isNull(b.getResFlowStepList().get(i).getId())){
                        b.getResFlowStepList().get(i).setUpdateAt(t);
                        b.getResFlowStepList().get(i).setCreateAt(t);
                        b.getResFlowStepList().get(i).setActive("Y");
                    }
                }
            }
            return this.edit(repo, DbResFlow.class, b);

        }catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }

    }
}