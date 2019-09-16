package com.pccw.backend.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pccw.backend.bean.JsonResult;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * SMPException
 */
@RestControllerAdvice(annotations = {RestController.class})
@Slf4j
public class SMPException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult exception(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<ErrMsg> eMsgs = new ArrayList<>();

        allErrors.forEach(objectError -> {
            FieldError fieldError = (FieldError)objectError;
            ErrMsg msg = new ErrMsg(fieldError.getField(),fieldError.getDefaultMessage());
            eMsgs.add(msg);
        });
        log.error("==============================");
        log.error("Validate Exception:{}",eMsgs);
        log.error("==============================");
        return JsonResult.fail(eMsgs);
    }
    
    @ExceptionHandler(Exception.class)
    public JsonResult runtimeExceptionHandler(Exception e) {
        log.error("------------------------------");
        log.error("Runtime Exception: {}", e.getMessage());
        log.error("------------------------------");
        return JsonResult.fail(e);
    }
    
}



