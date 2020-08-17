package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pccw.backend.annotation.JsonResultParamMapAnnotation;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * res_attr_value => product
 */

@Entity
@Table(name = "res_reservation")
@Data
@SequenceGenerator(name="id_reservation",sequenceName = "reservation_seq",allocationSize = 1)
//@JsonResultParamMapAnnotation(param1 = "id",param2 = "attrValue")
public class DbResReservation extends Base{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_reservation")
	private Long id;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "order_number")
	private String orderNo;

	@Column(name = "log_txtNum", length = 512)
	private String logTxtBum;

	@Column(name = "customer_type")
	private String customerType;

	@Column(name = "sku_id")
	private Long skuId;

	@Column(name = "repo_id")
	private Long repoId;

	@Column(name = "qty")
	private Long qty;

	@Column(name = "staff_number")
	private String staffId;

	@Column(name = "reservation_date")
	private Long reservationDate;

	@Column(name = "order_date")
	private Long orderDate;

	@Column(name = "payment_status")
	private String paymentStatus;

	@Column(name = "days")
	private Long days;

	// Y,N
	@Column(name = "selected")
	private String selected;

	@Column(name = "remark")
	private String remark;
}
