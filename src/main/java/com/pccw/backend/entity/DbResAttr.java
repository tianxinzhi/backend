package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * sku => product
 */

@Entity
@Table(name = "res_attr")
//@org.hibernate.annotations.Table(appliesTo = "res_attr",comment = "")
@Data
public class DbResAttr extends Base{

	@Id
	@GeneratedValue
	@Column(columnDefinition = "number(11)")
	private Long id;

	@Column(name = "attr_name",columnDefinition = "varchar2(64)")
	private String attrName;

	@Column(name = "attr_desc", columnDefinition = "varchar2(256)")
	private String attrDesc;

	@Column(name = "attr_value_type", columnDefinition = "varchar2(16)")
	private String attrValueType;

	@Column(name = "active", columnDefinition = "char(1)")
	private String active;

 //	@OneToMany(cascade = {CascadeType.MERGE},targetEntity = DbResAttrValue.class)
//	@JoinColumn(name = "id")
 //	private List<DbResAttrValue> attrValues;


}