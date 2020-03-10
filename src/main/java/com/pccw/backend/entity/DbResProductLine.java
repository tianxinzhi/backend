package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "res_productline")
@SequenceGenerator(name="id_productline",sequenceName = "productline_seq",allocationSize = 1)
public class DbResProductLine extends Base {
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_productline")
	private Long id;
	
	
	@Column(name = "pl_name", length = 255)
	private String plName;
	
	@Column(name = "pl_code", length = 255)
	private String plCode;

}
