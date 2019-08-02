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
 * class => category of sku
 */

@Data
@Entity
@Table(name = "res_class")
public class DbResClass implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;	
	
	@Column(name = "parent_class_id")
	private Long parentClassId;

	@Column(name = "class_Num", length = 32)
	private String classNum;
	
	@Column(name = "class_name", length = 32)
	private String className;
	
	
	@Column(name = "class_desc", length = 128)
	private String classDesc;
	
	
	@Column(name = "status", length = 3)
	private String status;
	

}
