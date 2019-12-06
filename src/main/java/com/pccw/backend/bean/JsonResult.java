package com.pccw.backend.bean;


import java.lang.Thread.State;
import java.util.Arrays;
import java.util.List;

import com.pccw.backend.exception.ErrMsg;

import lombok.AllArgsConstructor;
import lombok.Data;



/**
 * JsonResult is customer specifiaction of json of all api to return to client
 */
@Data
@AllArgsConstructor
public class JsonResult<T> {

    private String state;

    /**
     * 000 正常
     * 888 传参错误
     * 001 权限不足
     * 002 非法请求（没有登录）
     * 003 账号或者密码错误
     */
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
        return new JsonResult<T>("failed", "888",e.getMessage(), Arrays.asList());
    }
        /**
     * quick method to return a JsonResult when FAILED
     * @param <G> 
     * @param data
     * @return
     */
    public static <T> JsonResult<T> fail(List<T> data) {
        return new JsonResult<T>("failed","888", "API Validate Failed!", data);
    }

    public static <T> JsonResult<T> fail(ErrMsg e){
        return new JsonResult<>("failed", e.getCode(), e.getMsg(), Arrays.asList());
    }
}