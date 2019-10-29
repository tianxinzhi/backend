package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="res_sku_attr_value")
@Data
@SequenceGenerator(name="id_sku_attr_value",sequenceName = "sku_attr_value_seq",allocationSize = 1)
public class DbResSkuAttrValue extends Base {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sku_attr_value")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sku_id")
    private DbResSku sku;

//    @Column(name = "spec_id")
//    private long specId;

    @Column(name = "attr_id")
    private long attrId;

    @Column(name = "attr_value_id")
    private long attrValueId;
}
