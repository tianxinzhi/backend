package com.pccw.backend.entity;

import com.pccw.backend.annotation.JsonResultParamMapAnnotation;
import lombok.Data;

import javax.persistence.*;


/**
 * res_attr_value => product
 */

@Entity
@Table(name = "res_adjust_reason")
@Data
@SequenceGenerator(name="id_adjustReason",sequenceName = "adjustReason_seq",allocationSize = 1)
@JsonResultParamMapAnnotation(param1 = "id",param2 = "adjustReasonName",param3 = "remark")
public class DbResAdjustReason extends Base{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_adjustReason")
	private Long id;

	@Column(name = "adjust_reason_name",length = 64)
	private String adjustReasonName;

	@Column(name = "remark",length = 512)
	private String remark;
}
