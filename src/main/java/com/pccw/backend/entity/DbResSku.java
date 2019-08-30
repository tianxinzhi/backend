package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import lombok.Data;




/**
 * sku => product
 */

@Entity
@Table(name = "res_sku")
@Data
public class DbResSku implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue	
	private Long id;
	
	@Column(name = "class_id")
	private Long classId;

	@Column(name = "class_num")
	private String classNum;
	
	@Column(name = "sku_num", length = 12)
	private String skuNum;
	
	@Column(name = "sku_name", length = 32)
	private String skuName;
	
	@Column(name = "sku_desc", length = 512)
	private String skuDesc;

// 	@ManyToMany
//     @JoinTable(name="res_sku_repo",
//       		joinColumns = { @JoinColumn(name = "sku_id", referencedColumnName = "id") },
//       		inverseJoinColumns = { @JoinColumn(name = "repo_id", referencedColumnName = "id") }
//    )
// 	private List<DbResRepo> repos;
	
	@Column(name = "status", length = 3)
	private String status;
	
}
