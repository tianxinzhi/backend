package com.pccw.backend.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.NonNull;

/**
 * Base
 */

@Data
@MappedSuperclass
public class Base implements Serializable{

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue		
        private Long id;

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
}