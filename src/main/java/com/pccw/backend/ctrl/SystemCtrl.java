package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.system.LoginBean;
import com.pccw.backend.entity.DbResAccount;
import com.pccw.backend.repository.ResAccountRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("system")
@Api(value="SystemCtrl",tags={"system"})
public class SystemCtrl extends BaseCtrl<DbResAccount> {

    @Autowired
    ResAccountRepository repo;

    @ApiOperation(value="用户登录",tags={"system"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/login")
    public JsonResult login (@RequestBody LoginBean bean) {
        try {
            DbResAccount rwe = repo.getDbResAccountsByAccountNameAndAccountPassword(bean.getUsername(),bean.getPassword());
            if(rwe==null){
                return JsonResult.fail(new Exception());
            }
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }
}
