package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import javax.persistence.*;


/**
 * ROR = RESOURCE ORDER REQUEST
 */
@Data
@Entity
@Table(name = "res_log_ror_dtl")
@SequenceGenerator(name="id_logRorDtl",sequenceName = "logRorDtl_seq",allocationSize = 1)
public class DbResLogRorDtl extends BaseLogDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_logRorDtl")
	private Long id;

	@Column(name="dtl_skuId")
	private long dtlSkuId;


	@Column(name="dtl_itemId")
	private long dtlItemId;
	
	@Column(name="dtl_repoId")
	private long dtlRepoId;
	
	@Column(name="dtl_qty")
	private long dtlQty;

	@Column(name="ccc")
	private String ccc;

	@Column(name="wo")
	private String wo;

	@JsonBackReference
	@JoinColumn(name = "log_ror_id")
	@ManyToOne(targetEntity = DbResLogRor.class)
	private DbResLogMgt resLogMgt;
}
