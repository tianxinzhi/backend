package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * @description:
 * @author: ChenShuCheng
 * @create: 2020-07-30 09:30
 **/
@Data
@Table(name = "res_stock_return")
@Entity
@SequenceGenerator(name="id_stockReturn",sequenceName = "stockReturn_seq",allocationSize = 1)
@Where(clause = " active='Y' ")
public class DbResStockReturn extends Base{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_stockReturn")
    private Long id;

    @Column(name = "return_header",length = 512)
    private String returnHeaderId;

//    @Column(name = "return_line")
//    private String returnLineId;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "from_repo_id")
    private Long fromChannel;

    @Column(name = "to_repo_id")
    private Long toWareHouse;

    @Column(name = "qty")
    private Long qty;

    @Column(name = "return_date")
    private Long returnDate;

    @Column(name = "reason")
    private String reason;

    @Column(name = "status")
    private String status;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "log_txt_num", length = 512)
    private String logTxtNum;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "stock_return_id")
    private List<DbResStockReturnSerial> line;
}
