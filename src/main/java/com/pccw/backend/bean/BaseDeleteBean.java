package com.pccw.backend.bean;
/**
 * DeleteBean
 */


import java.util.ArrayList;

import lombok.Data;

@Data
public class BaseDeleteBean extends BaseBean {

    private ArrayList<Integer> ids;
}