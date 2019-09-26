package com.pccw.backend.ctrl;

import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.auth_account.CreateBean;
import com.pccw.backend.bean.auth_account.EditBean;
import com.pccw.backend.bean.auth_account.SearchBean;
import com.pccw.backend.entity.DbResAccount;
import com.pccw.backend.repository.ResAccountRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("auth_account")
@Api(value="Auth_AccountCtrl",tags={"auth_account"})
public class Auth_AccountCtrl extends BaseCtrl<DbResAccount> {

    @Autowired
    ResAccountRepository repo;

    @ApiOperation(value="创建用户",tags={"auth_account"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        System.out.println("bean:"+bean);
        return this.create(repo,DbResAccount.class,bean);
    }

    @ApiOperation(value="删除用户",tags={"auth_account"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids) {
        return this.delete(repo,ids);
    }

    @ApiOperation(value="修改用户",tags={"auth_account"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/edit")
    public JsonResult edit(@RequestBody EditBean b) {
        return this.edit(repo, DbResAccount.class, b);
    }

    @ApiOperation(value="搜索用户",tags={"auth_account"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/search")
    public JsonResult search(@RequestBody SearchBean bean) {
        return this.search(repo,bean);
    }

}
