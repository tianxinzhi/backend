package com.pccw.backend.bean;

import java.util.ArrayList;

import lombok.Data;

/**
 * JsonResult
 */
@Data
public class JsonResult {

    public String state;
    public String msg;
    public ArrayList data;
    public JsonResult(String state, String msg, ArrayList data) {
        super();
        this.state=state;
        this.msg=msg;
        this.data=data;

    }
}