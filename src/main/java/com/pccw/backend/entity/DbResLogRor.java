package com.pccw.backend.entity;

import lombok.Data;
import javax.persistence.*;


/**
 * ROR = RESOURCE ORDER REQUEST
 */
@Data
@Entity
@Table(name = "res_log_ror")
@SequenceGenerator(name="id_logRor",sequenceName = "logRor_seq",allocationSize = 1)
public class DbResLogRor extends BaseLog {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_logRor")
    private Long id;

    // POS / BOM / BES
    @Column(name = "log_sys",length = 8)
    private String logSys;

    // OrderId
    @Column(name="log_orderId",length = 512)
    private String logOrderId;

    // Related OrderId
    @Column(name="log_relatedOrderId",length = 512)
    private String logRelatedOrderId;

    // N(normal) / A(Advance Order)
    @Column(name="log_orderType",length = 1)
    private String logOrderType;




}
