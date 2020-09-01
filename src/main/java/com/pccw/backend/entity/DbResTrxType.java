package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * trx_type
 */
@Data
@Entity
@Table(name = "res_trx_type")
@SequenceGenerator(name="id_trxType",sequenceName = "trxType_seq",allocationSize = 1)
public class DbResTrxType extends Base {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_trxType")
	private Long id;

	@Column(name="transaction_type_name")
	private String transactionTypeName;

	@Column(name="description")
	private String description;

	@Column(name="transaction_action")
	private String transactionAction;

	@Column(name="transaction_source")
	private String transactionSource;

	@Column(name="disable_date")
	private Long disableDate;

}