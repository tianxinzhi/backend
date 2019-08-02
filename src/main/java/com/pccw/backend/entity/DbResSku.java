package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import javax.persistence.Table;

import lombok.Data;



/**
 * sku => product
 */

@Data
@Entity
@Table(name = "res_sku")
public class DbResSku implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long id;
	
	@Column(name = "class_id")
	private Long classId;

	@Column(name = "class_num")
	private String classNum;
	
	@Column(name = "sku_num", length = 12)
	private String skuNum;
	
	@Column(name = "sku_name", length = 32)
	private String skuName;
	
	@Column(name = "sku_desc", length = 512)
	private String skuDesc;
	
	@Column(name = "status", length = 3)
	private String status;
	
}
