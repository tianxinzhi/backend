package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.NamedQuery;

import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "res_sku")
@NamedQuery(name="DbResSku.findAll", query="SELECT a FROM DbResSku a")
public class DbResSku implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long id;
	
	@Column(name = "type_id")
	private Long typeId;
	
	@Column(name = "sku_num", length = 12)
	private String skuNum;
	
	@Column(name = "sku_name", length = 32)
	private String skuName;
	
	@Column(name = "sku_desc", length = 512)
	private String skuDesc;
	
	@Column(name = "key_item_attr")
	private Long keyItemAttr;
	
	@Column(name = "status", length = 3)
	private String status;
	
	@Column(name = "eff_start_date")
	private Date effStartDate;
	
	@Column(name = "eff_end_date")
	private Date effEndDate;
	
}
