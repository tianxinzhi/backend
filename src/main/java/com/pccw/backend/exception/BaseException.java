package com.pccw.backend.exception;

import lombok.Data;

/**
 * BaseException
 */

@Data
public class BaseException extends Exception {

    public static final String EXCEPTION_CODE_RUNTIMEEXCEPTION = "888";
    public static final String EXCEPTION_CODE_ACCANDPWDERROREXCEPTION = "003";
    public static final String EXCEPTION_CODE_NOLOGINEXCEPTION = "002";
    public static final String EXCEPTION_CODE_NORIGHTEXCEPTION = "001";
    public static final String EXCEPTION_CODE_ARGUMENTNOTVALIDEXCEPTION = "004";

    private String code;

    // private Exception exception;

    private String msg;
    
    public static BaseException getException(String code){
        BaseException e = new BaseException();
        e.setCode(code);
        return e;
    }
    // public static BaseException getException(String code,Exception exception){
    //     BaseException e = new BaseException();
    //     e.setCode(code);
    //     e.setException(exception);
    //     return e;
    // }
    private static BaseException getException(String code,String msg){
        BaseException e = new BaseException();
        e.setCode(code);
        e.setMsg(msg);
        return e;
    }
    public static BaseException getRuntimeException() {
        return BaseException.getException(BaseException.EXCEPTION_CODE_RUNTIMEEXCEPTION);
    }
    
    public static BaseException getAccAndPwdException() {
        return BaseException.getException(BaseException.EXCEPTION_CODE_ACCANDPWDERROREXCEPTION);
    }
    public static BaseException getArgumentNotValidException() {
        return BaseException.getException(BaseException.EXCEPTION_CODE_ARGUMENTNOTVALIDEXCEPTION);
    }
    public static BaseException getNoLoginException() {
        return BaseException.getException(BaseException.EXCEPTION_CODE_NOLOGINEXCEPTION);
    }
    public static BaseException getNoRightException() {
        return BaseException.getException(BaseException.EXCEPTION_CODE_NORIGHTEXCEPTION);
    }

}