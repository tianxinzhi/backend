package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;

/**
 * DbResFlow
 */

 @Entity
 @Data
 @Table(name = "res_flow")
 @SequenceGenerator(name="id_flow",sequenceName = "flow_seq",allocationSize = 1)
public class DbResFlow extends Base{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_flow")
    private Long id;


    @Column(name="flow_name")
    private String flowName;

    
}