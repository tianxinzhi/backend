package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;



import javax.persistence.Table;




/**
 * sku => product
 */

@Entity
@Table(name = "res_sku")
public class DbResSku implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue	
	public Long id;
	
	@Column(name = "class_id")
	public Long classId;

	@Column(name = "class_num")
	public String classNum;
	
	@Column(name = "sku_num", length = 12)
	public String skuNum;
	
	@Column(name = "sku_name", length = 32)
	public String skuName;
	
	@Column(name = "sku_desc", length = 512)
	public String skuDesc;
	
	@Column(name = "status", length = 3)
	public String status;
	
}
