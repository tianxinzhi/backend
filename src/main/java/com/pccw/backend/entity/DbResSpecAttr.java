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
@Table(name = "res_spec_attr")
public class DbResSpecAttr implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long id;
	
	@Column(name="spec_id")
	private Long specId;

	@Column(name="spec_ver")
	private Long specVer;
	
	@Column(name="attr_id")	
	private Long attrId;
	
	
	@Column(name="attr_value_id")
	private Long attrValueId;
		
	@Column(name = "attr_value_input", length = 25)
	private String attrValueInput;
	
	@Column(name = "eff_start_date")
	private Date effStartDate;
	
	@Column(name = "eff_end_date")
	private Date effEndDate;
	
}
