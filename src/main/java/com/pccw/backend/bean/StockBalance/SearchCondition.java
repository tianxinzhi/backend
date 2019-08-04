package com.pccw.backend.bean.StockBalance;

import java.io.Serializable;



/**
 * SearchCondition
 */
// @Data
public class SearchCondition implements Serializable{

    private static final long serialVersionUID = 1L;


    public String skuNum; // same as entity property and relative to the data clomun

    public String itemNum;

    public String repoNum;

    public Integer pageIndex;

    public Integer pageSize;



}