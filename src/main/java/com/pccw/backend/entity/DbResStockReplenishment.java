package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @description:
 * @author: XiaoZhi
 * @create: 2020-08-30 09:30
 **/
@Data
@Table(name = "res_stock_replenishment")
@Entity
@SequenceGenerator(name="id_stockReplenishment",sequenceName = "stockReplenishment_seq",allocationSize = 1)
public class DbResStockReplenishment extends Base{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_stockReplenishment")
    private Long id;

    @Column(name = "from_channel_id")
    private Long fromChannelId;

    @Column(name = "to_channel_id")
    private Long toChannelId;

    @Column(name = "from_channel_name")
    private String fromChannelName;

    @Column(name = "to_channel_name")
    private String toChannelName;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "stock")
    private Long stock;

    @Column(name = "qty")
    private Long qty;

    @Column(name = "last_replenish")
    private Long lastReplenish;

    @Column(name = "suggested_qty_1")
    private Long suggestedQty1;

    @Column(name = "suggested_qty_2")
    private Long suggestedQty2;

    @Column(name = "suggested_qty_3")
    private Long suggestedQty3;

    @Column(name = "request_date")
    private Long requestDate;

    @Column(name = "log_txt_num", length = 512)
    private String logTxtNum;

}
