package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "res_sku_type")
@Data
@SequenceGenerator(name="id_sku_type",sequenceName = "sku_type_seq",allocationSize = 1)
public class DbResSkuType extends Base {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sku_type")
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "sku_id")
    private DbResSku sku;

    @Column(name = "type_id")
    private long typeId;
}
