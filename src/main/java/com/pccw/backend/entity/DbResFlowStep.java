package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * DbResFlow
 */

 @Entity
 @Data
 @Table(name = "res_flow_step")
 @SequenceGenerator(name="id_flowStep",sequenceName = "flowStep_seq",allocationSize = 1)
public class DbResFlowStep extends Base{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_flowStep")
    private Long id;

    @Column(name="step_num")
    private String stepNum;

    @Column(name = "role_id", columnDefinition = "number(11)")
    private Long roleId;

    
}