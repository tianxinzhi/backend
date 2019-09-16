package com.pccw.backend.bean;


import java.lang.Thread.State;
import java.util.Arrays;
import java.util.List;

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
}