package com.pccw.backend.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;



import lombok.Data;
import lombok.NoArgsConstructor;




/**
 * SearchCondition
 */

@Data
@NoArgsConstructor
public class BaseSearchCondition implements Serializable{


    @NotNull
    private Integer pageIndex=0;
    @NotNull
    private Integer pageSize=10;



}