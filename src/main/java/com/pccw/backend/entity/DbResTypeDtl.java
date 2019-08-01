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

@Entity
@Table(name = "res_type_dtl")
@Data
public class DbResTypeDtl implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long id;
	
	@Column(name = "dtl_id")		
	private Long dtlId;
	
	@Column(name = "type_id")		
	private Long typeId;
	
	@Column(name = "sku_id")	
	private Long skuId;
	
	@Column(name = "spec_id")
	private Long specId;
	
	@Column(name = "type_attach_id")
	private Long typeAttachId;
	
	@Column(name = "type_skillset_id")
	private Long typeSkillsetId;
	
	@Column(name = "dtl_type", length = 3)	
	private String dtlType;
	
	@Column(name = "dtl_level", length = 8)	
	private String dtlLevel;
	
	@Column(name = "status", length = 3)	
	private String status;
	
	@Column(name = "eff_start_date")		
	private Date effStartDate;
	
	@Column(name = "eff_end_date")	
	private Date effEndDate;
	

}
