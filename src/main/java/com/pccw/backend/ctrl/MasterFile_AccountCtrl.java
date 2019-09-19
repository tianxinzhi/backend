package com.pccw.backend.ctrl;

import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_account.CreateBean;
import com.pccw.backend.bean.masterfile_account.EditBean;
import com.pccw.backend.bean.masterfile_account.LoginBean;
import com.pccw.backend.bean.masterfile_account.SearchBean;
import com.pccw.backend.entity.DbResAccount;
import com.pccw.backend.exception.SMPException;
import com.pccw.backend.repository.ResAccountRepository;
import com.pccw.backend.util.TokenGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("masterfile_account")
@Api(value="MasterFile_AccountCtrl",tags={"masterfile_account"})
public class MasterFile_AccountCtrl extends BaseCtrl<DbResAccount> {

    @Autowired
    ResAccountRepository repo;

    @ApiOperation(value="创建账户",tags={"masterfile_account"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        System.out.println("bean:"+bean);
        return this.create(repo,DbResAccount.class,bean);
    }

    @ApiOperation(value="删除账户",tags={"masterfile_account"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids) {
        return this.delete(repo,ids);
    }

    @ApiOperation(value="修改账户",tags={"masterfile_account"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/edit")
    public JsonResult edit(@RequestBody EditBean b) {
        return this.edit(repo, DbResAccount.class, b);
    }

    @ApiOperation(value="搜索账户",tags={"masterfile_account"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/search")
    public JsonResult search(@RequestBody SearchBean bean) {
        return this.search(repo,bean);
    }

    @ApiOperation(value="账户登录",tags={"masterfile_account"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/login")
    public JsonResult login (@RequestBody LoginBean bean) {
        System.out.println("loginBean"+bean);
        List<Map<String,String>> data = new ArrayList<>();
        Map<String,String> tokenMap = new HashMap<>();
        DbResAccount rwe = repo.getDbResAccountsByAccountNameAndPassword(bean.getAccountName(),bean.getPassword());
        if(rwe==null){
            return JsonResult.fail(new Exception());
        }
        String token = TokenGenerator.makeToken(bean);
        tokenMap.put("token",token);
        data.add(tokenMap);
        return JsonResult.success(data);
    }
}
