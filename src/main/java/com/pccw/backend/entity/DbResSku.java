package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * sku => product
 */

@Entity
@Table(name = "res_sku")
@Data
@SequenceGenerator(name="id_sku",sequenceName = "sku_seq",allocationSize = 1)
public class DbResSku extends Base {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sku")
	private Long id;

	@Column(name = "sku_code", length = 32)
	private String skuCode;

	@Column(name = "sku_desc", length = 512)
	private String skuDesc;

	@Column(name = "qty")
	private long qty;

	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "sku",orphanRemoval = true)
	private List<DbResSkuType> skuTypeList;

	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "sku",orphanRemoval = true)
	private List<DbResSkuAttrValue> skuAttrValueList;

}
