package com.pccw.backend.bean;
/**
 * DeleteBean
 */


import java.util.ArrayList;

import lombok.Data;

@Data
public class DeleteBean extends BaseBean{

    private ArrayList<Long> ids;
}