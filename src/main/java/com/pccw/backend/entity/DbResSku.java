package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import lombok.Data;




/**
 * sku => product
 */

@Entity
@Table(name = "res_sku")
@Data
@SequenceGenerator(name="id_sku",sequenceName = "sku_seq",allocationSize = 1)
public class DbResSku extends Base{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sku")
	private Long id;

	@Column(name = "class_id")
	private String classId;
	
	// @Column(name = "sku_num", length = 12)
	// private String skuNum;
	
	@Column(name = "sku_name", length = 32)
	private String skuName;
	
	@Column(name = "sku_code", length = 32)
	private String skuCode;

	@Column(name = "sku_desc", length = 512)
	private String skuDesc;

// 	@ManyToMany
//     @JoinTable(name="res_sku_repo",
//       		joinColumns = { @JoinColumn(name = "sku_id", referencedColumnName = "id") },
//       		inverseJoinColumns = { @JoinColumn(name = "repo_id", referencedColumnName = "id") }
//    )
// 	private List<DbResRepo> repos;

//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name="RES_SKU_TYPE",
//			joinColumns = { @JoinColumn(name = "SKU_ID")},
//			inverseJoinColumns = { @JoinColumn(name = "TYPE_ID") }
//	)
//	private List<DbResType> typeList;
	
	
}
