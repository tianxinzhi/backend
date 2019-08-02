package com.pccw.backend.bean.StockBalance;

import java.io.Serializable;

import lombok.Data;

/**
 * SearchCondition
 */
@Data
public class SearchCondition implements Serializable{

    public String skuNum; // same as entity property and relative to the data clomun

    public String itemNum;

    public String repoNum;

    public Integer pageIndex;

    public Integer pageSize;



}