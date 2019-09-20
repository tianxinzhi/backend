package com.pccw.backend.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;



/**
 * repository => store/shop
 */
@Data
@Entity
@Table(name = "res_subin")
public class DbResSubin extends Base {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "repo_id")
	private Long repoId;
	
	@Column(name = "subin_code")
	private String subinCode;

	@Column(name = "subin_type")
	private String subinType;

}
