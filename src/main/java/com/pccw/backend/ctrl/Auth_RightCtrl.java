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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/auth_right")
@Api(value="Auth_RightCtrl",tags={"auth_right"})
public class Auth_RightCtrl extends BaseCtrl<DbResRight>{

    @Autowired
    ResRightRepository repo;

    @ApiOperation(value="搜索权限",tags={"auth_right"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody @Valid SearchBean b) {
        return this.search(repo, b);
    }
    
    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    @ApiOperation(value="删除权限",tags={"auth_right"},notes="注意问题点")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
        return this.delete(repo,ids);
    }
    
    @RequestMapping(method = RequestMethod.POST,path="/create")
    @ApiOperation(value="新增权限",tags={"auth_right"},notes="注意问题点")
    public JsonResult create(@RequestBody CreateBean b){
        return this.create(repo, DbResRight.class, b);
    }
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    @ApiOperation(value="修改权限",tags={"auth_right"},notes="注意问题点")
    public JsonResult edit(@RequestBody EditBean b){
        log.info(b.toString());
        return this.edit(repo, DbResRight.class, b);
    }
}