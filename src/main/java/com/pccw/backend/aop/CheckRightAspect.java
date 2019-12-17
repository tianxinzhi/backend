package com.pccw.backend.aop;

import java.util.Arrays;

import com.alibaba.fastjson.JSONObject;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.exception.BaseException;
import com.pccw.backend.util.Session;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


/**
 * LastUpdatedBy: KEN
 * LastUpdatedAt: 5/12/2019
 * Desc: 验证是否用户是否拥有此权限
 */
@Aspect
@Component
@Slf4j
public class CheckRightAspect {

    @Autowired
    Session session;
    
    //execution表达式自行搜索引擎
    @Pointcut("execution(* com.pccw.backend.ctrl.*.*(..)) && !execution(* com.pccw.backend.ctrl.SystemCtrl.*(..)) && !execution(* com.pccw.backend.ctrl.CommonCtrl.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void printParam(JoinPoint joinPoint) throws BaseException {
        //获取请求的方法
        Signature sig = joinPoint.getSignature();
        String method = joinPoint.getTarget().getClass().getName() + "." + sig.getName();

        //获取请求的参数
        Object[] args = joinPoint.getArgs();
        //fastjson转换
        String params = JSONObject.toJSONString(args);

        //打印请求参数
        log.info(method + ":" + params);
        // 【1】获取session关于account相关right
        // 【2】获取ctrl名称（模块名称）和ctrl方法（模块按钮）
        // 【3】判断right
        // 【4】抛异常
        // throw BaseException.getNoLoginException();
        
    }
}