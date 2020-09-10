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
@Table(name = "res_stock_replenishment_header")
@Entity
@SequenceGenerator(name="id_stockReplenishmentHeader",sequenceName = "stockReplenishmentHeader_seq",allocationSize = 1)
@Where(clause = " active='Y'")
public class DbResStockReplenishmentHeader extends Base{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_stockReplenishmentHeader")
    private Long id;

    @Column(name = "log_txt_num", length = 512)
    private String logTxtNum;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "replenishment_header_id")
    private List<DbResStockReplenishment> line;
}
