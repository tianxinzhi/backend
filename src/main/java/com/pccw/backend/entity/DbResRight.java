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
 @SequenceGenerator(name="id_right",sequenceName = "right_seq",allocationSize = 1)
public class DbResRight extends Base{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_right")
    private Long id;

    @Column(name="right_pid")
    private Long rightPid;

    @Column(name="right_name")
    private String rightName;

    @Column(name="right_url")
    private String rightUrl;
    
}