package com.pccw.backend.bean;


import java.lang.Thread.State;
import java.util.Arrays;
import java.util.List;

import lombok.Data;



/**
 * JsonResult is customer specifiaction of json of all api to return to client
 */
@Data
public class JsonResult<T> {

    private String state;

    private String msg;

    private List<T> data;

    public JsonResult(String state, String msg, List<T> data) {
        super();
        this.state=state;
        this.msg=msg;
        this.data=data;

    }
    /**
     * quick method to return a JsonResult when SUCESSED
     * @param <G> 
     * @param data
     * @return
     */
    public static <T> JsonResult<T> success(List<T> data) {
        return new JsonResult<T>("success", "", data);
    }
    /**
     * quick method to return a JsonResult when FAILED
     * @return
     */
    public static <T> JsonResult<T> fail(Exception e){
        return new JsonResult<T>("failed", e.getMessage(), Arrays.asList());
    }
    
}