package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;


/**
 *  one area may have many reposity/shop/store
 */

@Data
@Deprecated
@Entity
@Table(name = "res_area")
@SequenceGenerator(name="id_area",sequenceName = "area_seq",allocationSize = 1)
public class DbResArea extends Base {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_area")
	private Long id;
	// @Id
	// @GeneratedValue
	// private Long id;

	@Column(name = "area_name", length = 64)
	private String areaName;

	@Column(name = "area_desc", length = 256)
	private String areaDesc;

	// @Column(name = "status", length = 6)
	// private String status;

}
