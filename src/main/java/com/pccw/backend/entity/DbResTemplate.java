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
@Table(name = "res_template")
public class DbResTemplate implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	@Column(name = "template_name", length = 16)
	private String templateName;
	
	@Column(name = "template_type", length = 16)
	private String templateType;
	
	@Column(name = "template_desc", length = 256)
	private String templateDesc;
	
	@Column(name = "is_preset", length = 1)
	private String isPreset;
	
	@Column(name = "eff_start_date")
	private Date effStartDate;
	
	@Column(name = "eff_end_date")
	private Date effEndDate;
	
	
}
