package com.pccw.backend.entity;


import javax.persistence.*;

import lombok.Data;



/**
 * repository => store/shop
 */
@Data
@Entity
@Table(name = "res_subin")
@SequenceGenerator(name="id_subin",sequenceName = "subin_seq",allocationSize = 1)
public class DbResSubin extends Base {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_subin")
	private Long id;

	@Column(name = "repo_id")
	private Long repoId;
	
	@Column(name = "subin_code")
	private String subinCode;

	@Column(name = "subin_type")
	private String subinType;

}
