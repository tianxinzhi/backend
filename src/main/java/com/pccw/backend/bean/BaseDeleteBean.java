package com.pccw.backend.bean;

/**
 * DeleteBean
 */

import java.util.ArrayList;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="公共 - BaseDeleteBean",description="")
public class BaseDeleteBean extends BaseBean {

    private ArrayList<Long> ids;
}