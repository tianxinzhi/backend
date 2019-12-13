package com.pccw.backend.bean;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * SearchCondition
 */
@Data
public class BaseBean implements Serializable {
    private long createAt;
    private long createBy;
    private long updateAt;
    private long updateBy;
    private String active;
    private String createAccountName;
    private String updateAccountName;
}
