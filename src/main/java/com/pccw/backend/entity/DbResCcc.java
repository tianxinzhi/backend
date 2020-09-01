package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Deprecated
@Entity
@Table(name = "res_ccc")
@SequenceGenerator(name="id_ccc",sequenceName = "ccc_seq",allocationSize = 1)
public class DbResCcc extends Base {


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_ccc")
	private Long id;


	@Column(name = "ccc_name", length = 255)
	private String cccName;

	@Column(name = "ccc_code", length = 255)
	private String cccCode;

}
