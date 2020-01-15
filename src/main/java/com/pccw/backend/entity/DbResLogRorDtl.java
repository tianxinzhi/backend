package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;


/**
 * ROR = RESOURCE ORDER REQUEST
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "res_log_ror_dtl")
@SequenceGenerator(name="id_logRorDtl",sequenceName = "logRorDtl_seq",allocationSize = 1)
public class DbResLogRorDtl extends BaseLogDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_logRorDtl")
	private Long id;

	@Column(name="dtl_skuId")
//	private long dtlSkuId;
	private String dtlSkuId;

	@Column(name="dtl_itemId")
//	private long dtlItemId;
	private String dtlItemId;

	@Column(name="dtl_repoId")
//	private long dtlRepoId;
	private String dtlRepoId;

	@Column(name="dtl_qty")
	private long dtlQty;

	// Shop code mapping 根据订单请求参数补加字段
	@Column(name = "ccc", length = 512)
	private String ccc;

	//  Work order 根据订单请求参数补加字段
	@Column(name = "wo", length = 512)
	private String wo;

	@JoinColumn(name = "log_ror_id")
	@ManyToOne(targetEntity = DbResLogRor.class)
	@JsonIgnoreProperties(value = { "item_details" })
	private DbResLogRor resLogRor;

}
