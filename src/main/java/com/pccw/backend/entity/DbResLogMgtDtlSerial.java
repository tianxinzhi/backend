package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * @description:
 * @author: XiaoZhi
 * @create: 2020-09-03 09:30
 **/
@Data
@Table(name = "res_log_mgt_dtl_serial")
@Entity
@SequenceGenerator(name="id_mgtDtlSerial",sequenceName = "mgtDtlSerial_seq",allocationSize = 1)
@Where(clause = " active='Y'")
public class DbResLogMgtDtlSerial extends Base{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_mgtDtlSerial")
    private Long id;

    @Column(name = "courier")
    private String courier;

    @Column(name = "serial")
    private String serial;

    @Column(name = "expiry_date")
    private Long expiryDate;

}
