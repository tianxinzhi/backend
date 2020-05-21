package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "res_process_dtl")
@SequenceGenerator(name = "id_process_dtl",sequenceName = "process_dtl_seq",allocationSize = 1)
public class DbResProcessDtl extends Base{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "id_process_dtl")
    private Long id;

    @Column(name = "flow_id")
    private Long flowId;

    @Column(name = "step_id")
    private Long stepId;

    @Column(name="step_num")
    private String stepNum;

    @Column(name = "role_id", columnDefinition = "number(11)")
    private Long roleId;

    @Column(name = "status")
    private String status;

    @Column(name = "remark",length = 512)
    private String remark;

}
