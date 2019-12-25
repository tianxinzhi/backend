package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pccw.backend.annotation.JsonResultParamHandle;
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

    @Column(name="flow_desc")
    private String flowDesc;

    @Column(name="flow_nature")
    private String flowNature;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "flow_id")
    private List<DbResFlowStep> resFlowStepList;

    
}