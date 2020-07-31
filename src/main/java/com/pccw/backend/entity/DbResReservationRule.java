package com.pccw.backend.entity;

import com.pccw.backend.annotation.JsonResultParamMapAnnotation;
import lombok.Data;

import javax.persistence.*;


/**
 * res_attr_value => product
 */

@Entity
@Table(name = "res_reservation_rule")
@Data
@SequenceGenerator(name="id_reservation_rule",sequenceName = "reservationRule_seq",allocationSize = 1)
//@JsonResultParamMapAnnotation(param1 = "id",param2 = "attrValue")
public class DbResReservationRule extends Base{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_reservation_rule")
	private Long id;

	@Column(name = "customer_type")
	private String customerType;

	@Column(name = "sku_id")
	private Long skuId;

	@Column(name = "payment_status")
	private String paymentStatus;

	@Column(name = "priority")
	private int priority;

}
