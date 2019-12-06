package com.pccw.backend.aop;

import com.alibaba.fastjson.JSONObject;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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

    //execution表达式自行搜索引擎
    @Pointcut("execution(* com.pccw.backend.ctrl.*.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void printParam(JoinPoint joinPoint){
        //获取请求的方法
        Signature sig = joinPoint.getSignature();
        String method = joinPoint.getTarget().getClass().getName() + "." + sig.getName();

        //获取请求的参数
        Object[] args = joinPoint.getArgs();
        //fastjson转换
        String params = JSONObject.toJSONString(args);

        //打印请求参数
        log.info(method + ":" + params);
    }
}