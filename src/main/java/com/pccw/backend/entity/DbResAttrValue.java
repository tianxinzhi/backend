package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pccw.backend.annotation.JsonResultParamHandle;
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
@JsonResultParamHandle(param1 = "id",param2 = "attrValue")
public class DbResAttrValue extends Base{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_attrValue")
	private Long id;

	@Column(name = "attr_value",columnDefinition = "varchar(128) ")
	private String attrValue;

	@Column(name = "unit_of_measure", columnDefinition = "varchar(16)  ")
	private String unitOfMeasure;

	@Column(name = "value_from", columnDefinition = "varchar(128)")
	private String valueFrom;

	@Column(name = "value_to", columnDefinition = "varchar(128)")
	private String valueTo;

	@JsonBackReference
	@JsonIgnoreProperties(value = { "attrAttrValueList" })
	@OneToMany(mappedBy = "attrValue",cascade = CascadeType.ALL)
	private List<DbResAttrAttrValue> attrAttrValueList;


}
