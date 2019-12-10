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
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    HttpServletRequest request;

    @ApiOperation(value="用户登录",tags={"system"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/login")
    public JsonResult login (@RequestBody LoginBean bean) {
        try {
            DbResAccount rwe = repo.getDbResAccountsByAccountNameAndAccountPassword(bean.getUsername(),bean.getPassword());
            if(Objects.isNull(rwe))
             return JsonResult.fail(BaseException.getAccAndPwdException());

            //取数据库用户数据
            // （代完成）
//            JSONObject obj = new JSONObject();
            Map<String,Object> map = new HashMap<>();
            map.put("role",rwe.getAccountRoles().stream().map(role->{
                return role.getRoleId();
            }).collect(Collectors.toList()));
            map.put("accountName",rwe.getAccountName());
            map.put("account",rwe.getId());
            JSONObject object = new JSONObject(map);

            //取sessionId为token，存session
//            String uuid = UUID.randomUUID().toString();
            String sessionId = request.getSession().getId();
            session.set(sessionId, object);
            
            return JsonResult.success(Arrays.asList(sessionId));
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="用户登出",tags={"system"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/logout")
    public JsonResult logout (@RequestBody LoginBean bean) {
        try {
            session.delete(session.getToken());
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }
}
