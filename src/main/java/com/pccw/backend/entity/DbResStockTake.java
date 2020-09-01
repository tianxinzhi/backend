package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @description:
 * @author: ChenShuCheng
 * @create: 2020-07-30 09:30
 **/
@Data
@Table(name = "res_stock_take")
@Entity
@SequenceGenerator(name="id_stockTake",sequenceName = "stockTake_seq",allocationSize = 1)
public class DbResStockTake extends Base{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_stockTake")
    private Long id;

    @Column(name = "stockTakeNumber",length = 512)
    private String stockTakeNumber;

    @Column(name = "channelId")
    private Long channelId;

    @Column(name = "completeTime")
    private Long completeTime;

    /**
     * 单据填写状态：completed/draft
     */
    @Column(name = "fillStatus")
    private String fillStatus;

    // Y , N
    @Column(name = "display_quantity",length = 8)
    private String displayQuantity;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)  //ALL  PERSIST
    @JoinColumn(name = "stock_take_id")
    private List<DbResStockTakeDtl> line;

}
