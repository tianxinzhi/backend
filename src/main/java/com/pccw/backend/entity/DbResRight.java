package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Entity;

import lombok.Data;

/**
 * DbResRight
 */

 @Entity
 @Data
 @Table(name = "res_right")
public class DbResRight implements Serializable{

    @Id
    @GeneratedValue 
    private long id;

    @Column(name="right_pid")
    private String rightPid;

    @Column(name="right_name")
    private String rightName;

    @Column(name="right_url")
    private String rightUrl;


    // @Column(name="right_pid")
    // private long rightPid;
    
}