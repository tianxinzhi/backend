package com.pccw.backend.bean;


import javax.validation.constraints.NotNull;



import lombok.Data;
import lombok.NoArgsConstructor;




/**
 * SearchCondition
 */

@Data
@NoArgsConstructor
public class BaseSearchBean extends BaseBean{


    @NotNull
    private Integer pageIndex=0;
    @NotNull
    private Integer pageSize=10;



}