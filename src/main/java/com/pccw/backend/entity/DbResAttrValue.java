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
@Table(name = "res_attr_value")
public class DbResAttrValue implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "attr_value", length = 128)
	private String attrValue;	
	
	@Column(name = "value_type", length = 3)
	private String valueType;
	
	@Column(name = "default_value", length = 128)
	private String defaultValue;
	
	@Column(name = "unit_of_measure", length = 16)
	private String unitOfMeasure;
	
	@Column(name = "value_from", length = 128)
	private String valueFrom;
	
	@Column(name = "value_to", length = 128)
	private String valueTo;
	
	@Column(name = "range_interval", length = 16)
	private String rangeInterval;
	
	@Column(name = "eff_start_date")
	private Date effStartDate;
	
	@Column(name = "eff_end_date")
	private Date effEndDate;


}
