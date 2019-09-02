package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.workflow_flow.*;
import com.pccw.backend.entity.DbResFlow;
import com.pccw.backend.repository.ResFlowRepository;
import com.pccw.backend.bean.DeleteBean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/workflow/flow")
public class WorkFlow_FlowCtrl extends BaseCtrl<DbResFlow>{

    @Autowired
    ResFlowRepository repo;

    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        return this.search(repo, SearchBean.class, b);
    }

    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody DeleteBean ids){
        return this.delete(repo,ids);
    }

    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody CreateBean b){
        return this.create(repo, DbResFlow.class, b);
    }
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult edit(@RequestBody EditBean b){
        return this.edit(repo, DbResFlow.class, b);
    }
}