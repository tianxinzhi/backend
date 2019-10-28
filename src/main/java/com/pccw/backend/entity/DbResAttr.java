package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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


//	@Column(name = "attr_name",columnDefinition = "varchar2(64)")
//	private String attrName;
//
//	@Column(name = "attr_desc", columnDefinition = "varchar2(256)")
//	private String attrDesc;
//
//	@Column(name = "attr_value_type", columnDefinition = "varchar2(16)")
//	private String attrValueType;

	@Column(name = "attr_name",columnDefinition = "varchar(64)")
	private String attrName;

	@Column(name = "attr_desc", columnDefinition = "varchar(256)")
	private String attrDesc;

	@JsonBackReference
	//@JsonIgnoreProperties(value = { "attrAttrValueList" })
	@OneToMany(mappedBy = "attr",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<DbResAttrAttrValue> attrAttrValueList;
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "res_attr_attr_value",
//			joinColumns = {@JoinColumn(name = "attr_id")},
//			inverseJoinColumns = {@JoinColumn(name = "attr_value_id")})
//	private List<DbResAttrValue> attrValueList;
 //	@OneToMany(cascade = {CascadeType.MERGE},targetEntity = DbResAttrValue.class)
//	@JoinColumn(name = "id")
 //	private List<DbResAttrValue> attrValues;


}
