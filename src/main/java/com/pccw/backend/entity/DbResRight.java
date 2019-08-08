package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * DbResRight
 */

 @Entity
 @Data
 @Table(name = "res_right")
public class DbResRight implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(name="right_module")
    private String rightModule;

    @Column(name="right_name")
    private String rightName;

    @Column(name="right_url")
    private String rightUrl;
    
}