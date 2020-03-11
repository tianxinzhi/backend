package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "res_workorder")
@SequenceGenerator(name="id_workorder",sequenceName = "ccc_workorder",allocationSize = 1)
public class DbResWorkOrder extends Base {
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_workorder")
	private Long id;
	
	
	@Column(name = "wo_name", length = 255)
	private String woName;
	
	@Column(name = "wo_code", length = 255)
	private String woCode;

}
