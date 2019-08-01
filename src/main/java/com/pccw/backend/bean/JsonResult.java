package com.pccw.backend.bean;

import java.util.ArrayList;

import lombok.Data;

/**
 * JsonResult
 */
@Data
public class JsonResult<T> {

    public String state;
    public String msg;
    public ArrayList<T> data;
    public JsonResult(String state, String msg, ArrayList<T> data) {
        super();
        this.state=state;
        this.msg=msg;
        this.data=data;

    }
}