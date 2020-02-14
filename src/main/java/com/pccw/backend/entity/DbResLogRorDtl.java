package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	private Long dtlSkuId;
//	private String dtlSkuId;

	@Column(name="dtl_itemId")
	private Long dtlItemId;
//	private String dtlItemId;

	@Column(name="dtl_repoId")
	private Long dtlRepoId;
//	private String dtlRepoId;

	@Column(name="dtl_qty")
	private Long dtlQty;

	@Column(name="ccc")
	private String ccc;

	@Column(name="wo")
	private String wo;

	@Column(name="detail_id")
	private String detailId;

	@JsonBackReference
	@JoinColumn(name = "log_ror_id")
	@ManyToOne(targetEntity = DbResLogRor.class)
	private DbResLogRor resLogRor;
}
