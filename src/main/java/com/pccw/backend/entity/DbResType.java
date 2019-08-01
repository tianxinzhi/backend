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


@Data
@Entity
@Table(name = "res_type")
public class DbResType implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	@Column(name = "type_code", length = 3)
	private String typeCode;
	
	@Column(name = "type_name", length = 32)
	private String typeName;
	
	@Column(name = "type_nature", length = 3)
	private String typeNature;
	
	@Column(name = "type_desc", length = 512)
	private String typeDesc;
	
	@Column(name = "status", length = 3)
	private String status;
	
	@Column(name = "eff_start_date")
	private Date effStartDate;
	
	@Column(name = "eff_end_date")
	private Date effEndDate;
}
