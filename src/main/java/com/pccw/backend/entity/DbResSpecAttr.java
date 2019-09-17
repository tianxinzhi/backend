package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;


/**
 * sku => product
 */

@Entity
@Table(name = "res_spec_attr")
@org.hibernate.annotations.Table(appliesTo = "res_spec_attr",comment = "spec data")
@Data
public class DbResSpecAttr extends Base{

	@Id
	@GeneratedValue
	@Column(columnDefinition = "number(11)")
	private Long id;

	@Column(name = "spec_id",columnDefinition = "number(11)")
	private String specId;

	@Column(name = "ver_id", columnDefinition = "varchar(16)")
	private String verId;

	@Column(name = "attr_id", columnDefinition = "number(11)")
	private String attrId;

	@Column(name = "attr_value_id", columnDefinition = "number(11)")
	private String attrValueId;

// 	@ManyToMany
//     @JoinTable(name="res_sku_repo",
//       		joinColumns = { @JoinColumn(name = "sku_id", referencedColumnName = "id") },
//       		inverseJoinColumns = { @JoinColumn(name = "repo_id", referencedColumnName = "id") }
//    )
// 	private List<DbResRepo> repos;


}