package com.pccw.backend.entity;


import javax.persistence.*;
import javax.persistence.Entity;

import lombok.Data;

/**
 * DbResRight
 */

 @Entity
 @Data
 @Table(name = "res_right")
public class DbResRight extends Base{

    @Id
    @GeneratedValue 
    private long id;

    @Column(name="right_pid")
    private Long rightPid;

    @Column(name="right_name")
    private String rightName;

    @Column(name="right_url")
    private String rightUrl;
    
}