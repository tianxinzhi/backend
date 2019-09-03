package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;



/**
 * repository => store/shop
 */
@Data
@Entity
@Table(name = "res_repo")
public class DbResRepo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;

	// @ManyToOne(cascade= {CascadeType.ALL})
	// @JoinColumn(name="area_id", referencedColumnName="id")
	// private DbResArea area;

	// @OneToMany
//     @JoinTable(name="res_sku_repo",
//       		joinColumns = { @JoinColumn(name = "sku_id", referencedColumnName = "id") },
//       		inverseJoinColumns = { @JoinColumn(name = "repo_id", referencedColumnName = "id") }
//    )
//    @JoinTable(name="res_sku_repo")
//    @JoinColumn(name = "id",referencedColumnName = "repo_id")
// 	private List<DbResSku> skus;

	// @Column(name = "repo_type", length = 6)
	// private String repoType;

	@Column(name = "area_id")
	private Long areaId;

	// @Column(name="repo_num",length = 64)
	// private String repoNum;
	
	@Column(name = "repo_name", length = 64)
	private String repoName;

	@Column(name = "repo_addr", length = 512)
	private String repoAddr;

	// @Column(name = "status", length = 6)	
	// private String status;

}
