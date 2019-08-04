package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



/**
 * repository => store/shop
 */
@Entity
@Table(name = "res_repo")
public class DbResRepo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	public Long id;

	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="area_id", referencedColumnName="id")
	public DbResArea area;

	@Column(name = "repo_type", length = 6)
	public String repoType;

	@Column(name="repo_num",length = 64)
	public String repoNum;
	
	@Column(name = "repo_name", length = 64)
	public String repoName;

	@Column(name = "repo_addr", length = 512)
	public String repoAddr;

	@Column(name = "status", length = 6)	
	public String status;

}
