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
@Table(name = "res_spec_ver")
public class DbResSpecVer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="spec_id")			
	private Long specId;
	
	@Column(name = "ver")
	private Long ver;
	
	@Column(name = "rev_name", length = 64)
	private String revName;
	
	@Column(name = "rev_format", length = 64)
	private String revFormat;
	
	@Column(name = "rev_num", length = 16)
	private String revNum;
	
	@Column(name = "rev_reason", length = 256)
	private String revReason;
	
	@Column(name = "rev_semantics", length = 256)
	private String revSemantics;
	
	@Column(name = "rev_date")
	private Date revDate;
	
	@Column(name = "eff_start_date")
	private Date effStartDate;
	
	@Column(name = "eff_end_date")
	private Date effEndDate;
}
