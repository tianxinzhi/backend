package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@Entity
@Table(name = "res_attr_grp_member")
@JsonRootName("data")
public class DbResAttrGrpMember implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	

	@Column(name = "attr_id")
	private Long attrId;
	
	@Column(name = "attr_grp_id")
	private Long attrGrpId;
	
	
	@Column(name = "relation_seq", length = 3)
	private String relationSeq;
		
	
	@Column(name = "eff_start_date", length = 16)
	private Date effStartDate;
	
	@Column(name = "eff_end_date")
	private Date effEndDate;
	
}
