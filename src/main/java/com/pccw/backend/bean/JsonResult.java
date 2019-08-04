package com.pccw.backend.bean;


import java.util.List;

import lombok.Data;

/**
 * JsonResult
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
}