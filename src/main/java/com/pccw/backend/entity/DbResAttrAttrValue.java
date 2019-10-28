package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;


/**
 * res_attr_value => product
 */

@Entity
@Table(name = "res_attr_attr_value")
@Data
public class DbResAttrAttrValue extends Base{

	@ManyToOne
	@JoinColumn(name = "attr_id" )
	private DbResAttr attr;

	@ManyToOne
	@JoinColumn(name = "attr_value_id")
	private DbResAttrValue attrValue;

}
