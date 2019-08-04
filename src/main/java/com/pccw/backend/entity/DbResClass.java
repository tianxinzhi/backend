package com.pccw.backend.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;

import javax.persistence.Table;



/**
 * class => category of sku
 */


@Entity
@Table(name = "res_class")
public class DbResClass implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue		
	public Long id;	
	
	@Column(name = "parent_class_id")
	public Long parentClassId;

	@Column(name = "class_Num", length = 32)
	public String classNum;
	
	@Column(name = "class_name", length = 32)
	public String className;
	
	
	@Column(name = "class_desc", length = 128)
	public String classDesc;
	
	
	@Column(name = "status", length = 3)
	public String status;
	

}
