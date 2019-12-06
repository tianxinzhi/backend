package com.pccw.backend.bean;


import java.lang.Thread.State;
import java.util.Arrays;
import java.util.List;

import com.pccw.backend.exception.BaseException;

import lombok.AllArgsConstructor;
import lombok.Data;



/**
 * JsonResult is customer specifiaction of json of all api to return to client
 */
@Data
@AllArgsConstructor
public class JsonResult<T> {

    private String state;

    private String code;

    private String msg;

    private List<T> data;

    /**
     * quick method to return a JsonResult when SUCESSED
     * @param <G> 
     * @param data
     * @return
     */
    public static <T> JsonResult<T> success(List<T> data) {
        return new JsonResult<T>("success", "000","", data);
    }
    /**
     * quick method to return a JsonResult when FAILED
     * @return
     */
    public static <T> JsonResult<T> fail(Exception e){
        BaseException baseException = BaseException.getRuntimeException();
        baseException.setMsg(e.getMessage());
        return new JsonResult<T>("failed", baseException.getCode(),baseException.getMsg(), Arrays.asList());
    }
    /**
     * quick method to return a JsonResult when FAILED
     * @return
     */
    public static <T> JsonResult<T> fail(BaseException e){
        return new JsonResult<T>("failed", e.getCode(),e.getMsg(), Arrays.asList());
    }
}