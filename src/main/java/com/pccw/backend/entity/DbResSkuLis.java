package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * sku => product
 */

@Entity
@Table(name = "res_sku_lis")
@Data
@SequenceGenerator(name="id_skuLis",sequenceName = "skuLis_seq",allocationSize = 1)
//@JsonResultParamHandle(param1 = "id",param2 = "skuCode")
public class DbResSkuLis extends Base {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_skuLis")
	private Long id;

	@Column(name = "repo_id", length = 32)
	private Long repoId;

	@OneToOne
	@JoinColumn(name = "sku_id", referencedColumnName = "id")
	private DbResSku skuId;//外键关联res_sku

//	@JsonBackReference
//	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
//	@JoinColumn(name = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "class_lis_id",referencedColumnName = "id")
	private DbResClassLis classLisId;

	@Column(name = "sku_code")
	private String skuCode;

	@Column(name = "sku_name")
	private String skuName;

	@Column(name = "sku_desc")
	private String skuDesc;

	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "skuLis",orphanRemoval = true)
	private List<DbResSkuAttrValueLis> skuAttrValueLisList;

}
