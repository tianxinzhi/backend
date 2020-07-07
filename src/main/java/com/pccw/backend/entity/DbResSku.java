package com.pccw.backend.entity;

import com.pccw.backend.annotation.JsonResultParamMapAnnotation;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


/**
 * sku => product
 */

@Entity
@Table(name = "res_sku")
@Getter
@Setter
@SequenceGenerator(name="id_sku",sequenceName = "sku_seq",allocationSize = 1)
@JsonResultParamMapAnnotation(param1 = "id",param2 = "skuCode")
public class DbResSku extends Base {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sku")
	private Long id;

	@Column(name = "sku_code", length = 32)
	private String skuCode;

	@Column(name = "sku_desc", length = 512)
	private String skuDesc;

	@Column(name = "sku_name")
	private String skuName;

	@Column(name = "sku_origin",length = 32)
	private String skuOrigin;

//	@JsonBackReference
//	@OneToMany(cascade = CascadeType.ALL,mappedBy = "sku",orphanRemoval = true)
//	private List<DbResSkuType> skuTypeList;
//
//	@JsonBackReference
//	@OneToMany(cascade = CascadeType.ALL,mappedBy = "sku",orphanRemoval = true)
//	private List<DbResSkuAttrValue> skuAttrValueList;


//	@OneToMany(cascade={CascadeType.ALL},mappedBy = "sku",orphanRemoval = true)
//	private List<DbResSkuRepo> skuRepoList;

//    @OneToOne(cascade = {CascadeType.ALL},mappedBy = "sku",orphanRemoval = true)
//    private DbResTypeSkuSpec dbResTypeSkuSpec;

	@OneToMany(cascade = {CascadeType.ALL},mappedBy = "sku",orphanRemoval = true)
	private List<DbResTypeSkuSpec> dbResTypeSkuSpec;
}
