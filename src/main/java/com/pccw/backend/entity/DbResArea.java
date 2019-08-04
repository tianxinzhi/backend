package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 *  one area may have many reposity/shop/store
 */


@Entity
@Table(name = "res_area")
public class DbResArea implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	public Long id;
	
	@Column(name = "area_name", length = 64)
	public String areaName;
	
	@Column(name = "area_desc", length = 256)
	public String areaDesc;
	
	@Column(name = "status", length = 6)	
	public String status;

}
