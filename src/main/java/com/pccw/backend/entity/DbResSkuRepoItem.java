package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Deprecated
@Entity
@Table(name = "res_skuRepoItem")
@SequenceGenerator(name="id_skuRepoItem",sequenceName = "skuRepoItem_seq",allocationSize = 1)
public class DbResSkuRepoItem extends Base{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_skuRepoItem")
    private Long id;


    @Column(name = "item_code", length = 128)
    private String itemCode;

    @ManyToOne
    @JsonIgnoreProperties(value = { "skuRepoItemList" })
    @JoinColumn(name = "res_sku_repo_id")
    private DbResSkuRepo skuRepo;

}
