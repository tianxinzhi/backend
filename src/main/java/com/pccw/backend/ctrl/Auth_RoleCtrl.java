package com.pccw.backend.ctrl;

import java.util.Arrays;
import java.util.List;

import com.pccw.backend.bean.auth_role.*;
import com.pccw.backend.bean.DeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.entity.DbResRole;
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

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@RequestMapping("/auth_role")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Auth_RoleCtrl extends BaseCtrl<DbResRole>{

    @Autowired
    ResRoleRepository repo;

    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody SearchBean b) {
        return this.search(repo, SearchBean.class, b);
    }

    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody DeleteBean ids){
        return this.delete(repo,ids);
    }

    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody CreateBean b){
        return this.create(repo, DbResRole.class, b);
    }
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult edit(@RequestBody EditBean b){
        return this.edit(repo, DbResRole.class, b);
    }
    
}