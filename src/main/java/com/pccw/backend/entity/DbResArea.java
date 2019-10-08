package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;



/**
 *  one area may have many reposity/shop/store
 */

@Data
@Entity
@Table(name = "res_area")
public class DbResArea extends Base {

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
