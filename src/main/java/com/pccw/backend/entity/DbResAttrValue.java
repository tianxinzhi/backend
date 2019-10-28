package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * res_attr_value => product
 */

@Entity
@Table(name = "res_attr_value")
@Data
@SequenceGenerator(name="id_attrValue",sequenceName = "attrValue_seq",allocationSize = 1)
public class DbResAttrValue extends Base{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_attrValue")
	private Long id;


	//@ManyToOne
	//@JoinColumn(name = "attr_id")
	//private DbResAttr attr;

//	@Column(name = "attr_id",columnDefinition = "number(11) ")
//	private Long attrId;
//
//	@Column(name = "attr_value",columnDefinition = "varchar(128) ")
//	private String attrValue;
//
//	@Column(name = "unit_of_measure", columnDefinition = "varchar(16)  ")
//	private String unitOfMeasure;
//
//	@Column(name = "value_from", columnDefinition = "varchar(128)")
//	private String valueFrom;
//
//	@Column(name = "value_to", columnDefinition = "varchar(128)")
//	private String valueTo;


// 	@ManyToMany
//     @JoinTable(name="res_sku_repo",
//       		joinColumns = { @JoinColumn(name = "sku_id", referencedColumnName = "id") },
//       		inverseJoinColumns = { @JoinColumn(name = "repo_id", referencedColumnName = "id") }
//    )
// 	private List<DbResRepo> repos;
@Column(name = "attr_value",columnDefinition = "varchar(128) ")
private String attrValue;

	@Column(name = "unit_of_measure", columnDefinition = "varchar(16)  ")
	private String unitOfMeasure;

	@Column(name = "value_from", columnDefinition = "varchar(128)")
	private String valueFrom;

	@Column(name = "value_to", columnDefinition = "varchar(128)")
	private String valueTo;

//	@JsonBackReference
//	@ManyToMany(mappedBy = "attrValueList",cascade = {CascadeType.ALL})
//	private List<DbResAttr> attrList;

	@JsonBackReference
	@JsonIgnoreProperties(value = { "attrAttrValueList" })
	@OneToMany(mappedBy = "attrValue",cascade = CascadeType.PERSIST)
	private List<DbResAttrAttrValue> attrAttrValueList;


}
