package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Base
 */

@Data
@MappedSuperclass
public class Base implements Serializable{

        private static final long serialVersionUID = 1L;

//        @Id
//        @GeneratedValue(strategy= GenerationType.SEQUENCE)
//        private Long id;

        @Column(name="create_at")
        private long createAt;


        @Column(name="update_at")
        private long updateAt;


        @Column(name="create_by")
        private long createBy;


        @Column(name="update_by")
        private long updateBy;


        // Y - avaliable , N - deleted
        @Column(name = "active",length = 4)
        private String active;

        //详情展示createBy用
        @Transient
        private String createAccountName;

        //详情展示updateBy用
        @Transient
        private String updateAccountName;
}
