package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @description:
 * @author: XiaoZhi
 * @create: 2020-09-03 09:30
 **/
@Data
@Table(name = "res_sku_repo_serial")
@Entity
@SequenceGenerator(name="id_skuRepoSerial",sequenceName = "skuRepoSerial_seq",allocationSize = 1)
//@Where(clause = " active='Y'")
public class DbResSkuRepoSerial extends Base{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_skuRepoSerial")
    private Long id;

    @Column(name = "courier")
    private String courier;

    @Column(name = "serial")
    private String serial;

    @Column(name = "expiry_date")
    private Long expiryDate;

    @ManyToOne(targetEntity = DbResSkuRepo.class)
    @JsonBackReference
    @JoinColumn(name = "sku_repo_id")
    private DbResSkuRepo skuRepo;

}
