package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "res_process")
@SequenceGenerator(name = "id_process",sequenceName = "process_seq",allocationSize = 1)
public class DbResProcess extends Base{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "id_process")
    private Long id;

    @Column(name="log_txtNum",length = 512)
    private String logTxtBum;

    @Column(name = "repo_id")
    private Long repoId;

    @Column(name = "status")
    private String status;

    @Column(name = "log_orderNature", length = 8)
    private String logOrderNature;

    @Column(name = "flow_id")
    private Long flowId;

    @Column(name = "remark",length = 512)
    private String remark;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "process_id")
    private List<DbResProcessDtl> processDtls;
}
