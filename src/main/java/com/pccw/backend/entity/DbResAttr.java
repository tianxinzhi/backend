package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pccw.backend.annotation.JsonResultParamMapAnnotation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


/**
 * sku => product
 */

@Entity
@Table(name = "res_attr")
//@org.hibernate.annotations.Table(appliesTo = "res_attr",comment = "")
@Getter
@Setter
@SequenceGenerator(name="id_attr",sequenceName = "attr_seq",allocationSize = 1)
@JsonResultParamMapAnnotation(param1 = "id",param2 = "attrName")
public class DbResAttr extends Base{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_attr")
	private Long id;

	@Column(name = "attr_name",columnDefinition = "varchar(64)")
	private String attrName;

	@Column(name = "attr_desc", columnDefinition = "varchar(256)")
	private String attrDesc;

	@JsonBackReference
	//@JsonIgnoreProperties(value = { "attrAttrValueList" })
	@OneToMany(mappedBy = "attr",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<DbResAttrAttrValue> attrAttrValueList;

}
