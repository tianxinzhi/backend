package com.pccw.backend.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.system.LoginBean;
import com.pccw.backend.entity.DbResAccount;
import com.pccw.backend.exception.BaseException;
import com.pccw.backend.repository.ResAccountRepository;
import com.pccw.backend.util.Session;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("system")
@Api(value="SystemCtrl",tags={"system"})
public class SystemCtrl extends BaseCtrl<DbResAccount> {

    @Autowired
    ResAccountRepository repo;

    @Autowired
    Session session;

    @Autowired
    HttpSession hs;

    @ApiOperation(value="用户登录",tags={"system"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/login")
    public JsonResult login (@RequestBody LoginBean bean) {
        try {
            DbResAccount rwe = repo.getDbResAccountsByAccountNameAndAccountPassword(bean.getUsername(),bean.getPassword());
            if(Objects.isNull(rwe))
             return JsonResult.fail(BaseException.getAccAndPwdException());
            // if(rwe==null){
            //     return JsonResult.fail(new Exception());
            // }
            JSONObject json = new JSONObject();
            json.put("name", "ken");
            json.put("age","18");
            session.set(hs.getId(), json);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="用户登出",tags={"system"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/logout")
    public JsonResult logout (@RequestBody LoginBean bean) {
        try {
            session.delete(hs.getId());
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }
}
