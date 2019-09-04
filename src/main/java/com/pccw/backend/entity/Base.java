package com.pccw.backend.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.lang.Nullable;

import lombok.NonNull;

/**
 * Base
 */

@MappedSuperclass
public class Base implements Serializable{

        private static final long serialVersionUID = 1L;

        // @Column(name="create_at")
        // @Nullable
        // private Date createAt;
    

        // @Column(name="update_at")
        // @Nullable
        // private Date updateAt;
    

        // @Column(name="create_by")
        // @Nullable
        // private long createBy;
    

        // @Column(name="update_by")
        // @Nullable
        // private long updateBy;

        // @Nullable
        // @NonNull
        // @Column(name = "status", columnDefinition = "varchar(4) default 'Y'")
        // private String status;
}