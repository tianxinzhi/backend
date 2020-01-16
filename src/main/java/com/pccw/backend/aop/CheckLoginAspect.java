package com.pccw.backend.aop;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.pccw.backend.exception.BaseException;
import com.pccw.backend.util.Session;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;


/**
 * LastUpdatedBy: KEN
 * LastUpdatedAt: 5/12/2019
 * Desc: 验证是否未登录，非法请求
 */

@Aspect
@Component
@Slf4j
public class CheckLoginAspect {


    @Autowired
    Session session;

    //execution表达式自行搜索引擎
    @Pointcut("execution(* com.pccw.backend.ctrl.*.*(..)) && !execution(* com.pccw.backend.ctrl.SystemCtrl.*(..)) && !execution(* com.pccw.backend.ctrl.CommonCtrl.*(..))")

    public void pointcut() {}

    @Before("pointcut()")
    public void printParam(JoinPoint joinPoint) throws BaseException {


         if(!session.isLogin(session.getToken())){
             throw BaseException.getNoLoginException();
         }

    }
}