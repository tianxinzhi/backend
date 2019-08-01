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
@Table(name = "res_attr_value_grp_member")
public class DbresAttrValueGrpMember implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "attr_value_grp_id")
	private Long attrValueGrpId;
	
	@Column(name = "attr_valueId")
	private Long attrValueId;
	
	@Column(name = "relation_seq", length = 3)
	private String relationSeq;
	
	@Column(name = "eff_start_date", length = 16)
	private Date effStartDate;
	
	@Column(name = "eff_end_date")
	private Date effEndDate;
}
