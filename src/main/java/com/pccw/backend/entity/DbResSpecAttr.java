package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;


/**
 * sku => product
 */

@Entity
@Table(name = "res_spec_attr")
// @org.hibernate.annotations.Table(appliesTo = "res_spec_attr",comment = "spec data")
@Data
@SequenceGenerator(name="id_specAttr",sequenceName = "specAttr_seq",allocationSize = 1)
public class DbResSpecAttr extends Base{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_specAttr")
	private Long id;

	@Column(name = "ver_id", columnDefinition = "varchar(16)")
	private String verId;

	@Column(name = "attr_id", columnDefinition = "number(11)")
	private String attrId;

	@Column(name = "attr_value_id", columnDefinition = "number(11)")
	private String attrValueId;

}
