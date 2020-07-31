package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

/**
 * @description:
 * @author: ChenShuCheng
 * @create: 2020-07-30 09:59
 **/
@Data
@Table(name = "res_stock_take_dtl")
@Entity
@SequenceGenerator(name="id_stockTakeDtl",sequenceName = "stockTakeDtl_seq",allocationSize = 1)
public class DbResStockTakeDtl extends Base{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_stockTakeDtl")
    private Long id;

    @Column(name = "skuId")
    private Long skuId;

    @Column(name = "stockTakeOne")
    private String stockTakeOne;

    @Column(name = "stockTakeTwo")
    private String stockTakeTwo;

    @Column(name = "stockTakeThree")
    private String stockTakeThree;

    @Column(name = "stockTakeBalance")
    private String stockTakeBalance;

    @Column(name = "difference")
    private String difference;

    @JsonBackReference
    @JoinColumn(name = "stock_take_id")
    @ManyToOne(targetEntity = DbResStockTake.class)
    private DbResStockTake resStockTake;
}
