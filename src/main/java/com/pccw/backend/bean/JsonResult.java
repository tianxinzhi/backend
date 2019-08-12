package com.pccw.backend.bean;


import java.util.Arrays;
import java.util.List;

import lombok.Data;

/**
 * JsonResult is customer specifiaction of json of all api to return to client
 */
@Data
public class JsonResult<T> {

    public String state;
    public String msg;
    public List<T> data;
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
    public static <G> JsonResult<G> success(List<G> data) {
        return new JsonResult<G>("success", "", data);
    }
    /**
     * quick method to return a JsonResult when FAILED
     * @param <G>
     * @return
     */
    public static <G>JsonResult<G> fail(){
        return new JsonResult<G>("failed", "", Arrays.asList());
    }
}