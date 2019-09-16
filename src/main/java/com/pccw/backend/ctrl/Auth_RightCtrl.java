package com.pccw.backend.ctrl;



import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.auth_right.*;
import com.pccw.backend.entity.DbResRight;
import com.pccw.backend.repository.ResRightRepository;

import javax.validation.Valid;

import com.pccw.backend.bean.BaseDeleteBean;

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
@RequestMapping("/auth_right")
public class Auth_RightCtrl extends BaseCtrl<DbResRight>{

    @Autowired
    ResRightRepository repo;

    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody @Valid SearchBean b) {
        return this.search(repo, b);
    }

    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
        return this.delete(repo,ids);
    }

    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody CreateBean b){
        return this.create(repo, DbResRight.class, b);
    }
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult edit(@RequestBody EditBean b){
        log.info(b.toString());
        return this.edit(repo, DbResRight.class, b);
    }
}