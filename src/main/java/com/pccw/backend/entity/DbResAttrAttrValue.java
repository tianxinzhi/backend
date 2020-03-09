package com.pccw.backend.entity;

import com.pccw.backend.annotation.JsonResultParamHandle;
import lombok.Data;

import javax.persistence.*;


/**
 * res_attr_value => product
 */

@Entity
@Table(name = "res_attr_attr_value")
@Data
@SequenceGenerator(name="id_attrAttrValue",sequenceName = "attrAttrValue_seq",allocationSize = 1)
@JsonResultParamHandle(param1 = "attr",param2 = "attrValue")
public class DbResAttrAttrValue extends Base{

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_attrAttrValue")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "attr_id" )
	private DbResAttr attr;

	@ManyToOne
	@JoinColumn(name = "attr_value_id")
	private DbResAttrValue attrValue;

}
