package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * @description:
 * @author: ChenShuCheng
 * @create: 2020-07-30 09:59
 **/
@Data
@Table(name = "res_stock_return_serial")
@Entity
@SequenceGenerator(name="id_stockReturnSerialDtl",sequenceName = "stockReturnSerial_seq",allocationSize = 1)
public class DbResStockReturnSerial extends Base{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_stockReturnSerialDtl")
    private Long id;

    @Column(name = "skuId")
    private Long skuId;

    @Column(name = "return_line")
    private String returnLineId;

    @Column(name = "serial_no")
    private String serialNo;

    @Column(name = "expiry_date")
    private Long expiryDate;

}
