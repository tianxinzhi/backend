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
@Table(name = "res_spec")
public class DbResSpec implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long id;
	
	@Column(name = "spec_name", length = 64)
	private String specName;
	
	@Column(name = "spec_desc", length = 512)
	private String specDesc;
	
	@Column(name = "spec_url", length = 256)
	private String specUrl;
	
	@Column(name = "spec_status", length = 1)
	private String specStatus;
	
	@Column(name = "ver_prefer", length = 16)
	private String verPrefer;
	
	@Column(name = "ver_min", length = 16)
	private String verMin;
	
	@Column(name = "eff_start_date")
	private Date effStartDate;
	
	@Column(name = "eff_end_date")
	private Date effEndDate;
}
