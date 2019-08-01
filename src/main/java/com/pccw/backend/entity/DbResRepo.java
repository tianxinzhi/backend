package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "res_repo")
public class DbResRepo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="area_id", referencedColumnName="id")
	private DbResArea area;

	@Column(name = "repo_type", length = 6)
	private String repoType;
	
	@Column(name = "repo_name", length = 64)
	private String repoName;

	@Column(name = "repo_addr", length = 512)
	private String repoAddr;

	@Column(name = "status", length = 6)	
	private String status;

}
