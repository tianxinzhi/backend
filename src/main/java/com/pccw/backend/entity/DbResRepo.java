package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import lombok.Data;



/**
 * repository => store/shop
 */
@Data
@Entity
@Table(name = "res_repo")
@SequenceGenerator(name="id_repo",sequenceName = "repo_seq",allocationSize = 1)
public class DbResRepo extends Base {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_repo")
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

	@Column(name="repo_code",length = 64)
	private String repoCode;
	
	@Column(name = "repo_name", length = 64)
	private String repoName;

	@Column(name = "repo_addr", length = 512)
	private String repoAddr;

	@Column(name = "repo_type", length = 11)
	private String repoType;


}
