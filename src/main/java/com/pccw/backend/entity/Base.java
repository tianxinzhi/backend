package com.pccw.backend.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Base
 */

@MappedSuperclass
public class Base implements Serializable{

        private static final long serialVersionUID = 1L;

        @Column(name="create_at")
        private Date createAt;
    

        @Column(name="update_at")
        private Date updateAt;
    

        @Column(name="create_by")
        private long createBy;
    

        @Column(name="update_by")
        private long updateBy;

        @Column(name = "status", length = 6)	
        private String status;
}