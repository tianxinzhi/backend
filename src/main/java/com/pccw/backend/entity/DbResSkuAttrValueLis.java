package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="res_sku_attr_value_lis")
@Data
@SequenceGenerator(name="id_sku_attr_value_lis",sequenceName = "sku_attr_value_Lis_seq",allocationSize = 1)
public class DbResSkuAttrValueLis extends Base {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sku_attr_value_lis")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sku_attr_value_id",referencedColumnName = "id")
    private DbResSkuAttrValue skuAttrValueId;//外键

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "sku_lis_id")
    private DbResSkuLis skuLis;


    @Column(name = "attr_name")
    private String attrName;

    @Column(name = "attr_value")
    private String attrValue;
}
