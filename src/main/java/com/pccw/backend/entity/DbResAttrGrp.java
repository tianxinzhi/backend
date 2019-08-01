package com.pccw.backend.entity;

import java.io.Serializable;

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
@Table(name = "res_attr_grp")
@JsonRootName("data")
public class DbResAttrGrp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "attr_grp_name", length = 16)
	private String attrGrpName;
	
	@Column(name = "relation_type", length = 8)
	private String relationType;
	
	@Column(name = "attr_grp_desc", length = 256)
	private String attrGrpDesc;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

		
	
}
