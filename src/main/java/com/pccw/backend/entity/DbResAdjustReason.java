package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * res_attr_value => product
 */

@Entity
@Table(name = "res_adjust_reason")
@Data
public class DbResAdjustReason extends Base{

	// @Id
	// @GeneratedValue
	// @Column(columnDefinition = "number(11)")
	// private Long id;


	@Column(name = "adjust_reason_name",length = 64)
	private String adjustReasonName;


}
