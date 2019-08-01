package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "res_attr_value_grp")
public class DbResAttrValueGrp implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "attr_value_grp_name", length = 16)
	private String attrValueGrpName;
	
	@Column(name = "relation_type", length = 8)
	private String relationType;
	
	@Column(name = "attr_value_grp_desc", length = 256)
	private String attrValueGrpDesc;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;


}
