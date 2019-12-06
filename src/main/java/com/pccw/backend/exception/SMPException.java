package com.pccw.backend.exception;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pccw.backend.bean.JsonResult;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * Author: KEN
 * Date: 2/12/2019
 * Desc: 所有控制器全局异常处理，并统一返回前端
 */
@RestControllerAdvice(annotations = {RestController.class})
@Slf4j
public class SMPException {

    //无效传参处理
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult exception(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();

        JSONArray arr = new JSONArray();

        allErrors.forEach(objectError -> {
            FieldError fieldError = (FieldError)objectError;
            JSONObject obj = new JSONObject();
            obj.put("code", fieldError.getField());
            obj.put("msg",fieldError.getDefaultMessage());
            arr.add(obj);
        });
        log.error("==============================");
        log.error("Validate Exception:{}",arr.toJSONString());
        log.error("==============================");
        BaseException baseException = BaseException.getArgumentNotValidException();
        baseException.setMsg(arr.toJSONString());
        return JsonResult.fail(baseException);
    }

    //其他运行时异常处理
    @ExceptionHandler(Exception.class)
    public JsonResult runtimeExceptionHandler(Exception e) {
        log.error("------------------------------");
        log.error("Runtime Exception: {}", e.getMessage());
        log.error("------------------------------");
        return JsonResult.fail(BaseException.getRuntimeException());
    }

    //自定义业务逻辑相关异常处理
    @ExceptionHandler(BaseException.class)
    public JsonResult baseExceptionHandler(Exception e) {
        log.error("------------------------------");
        log.error("Runtime Exception: {}", ((BaseException)e).getMsg());
        log.error("------------------------------");
        return JsonResult.fail((BaseException)e);
    }

}



