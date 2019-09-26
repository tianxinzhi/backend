package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.system.LoginBean;
import com.pccw.backend.entity.DbResAccount;
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
@RequestMapping("system")
@Api(value="SystemCtrl",tags={"system"})
public class SystemCtrl extends BaseCtrl<DbResAccount> {

    @Autowired
    ResAccountRepository repo;

    @ApiOperation(value="用户登录",tags={"system"},notes="注意问题点")
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
