package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import javax.persistence.*;


/**
 * ROR = RESOURCE ORDER REQUEST
 */
@Data
@Entity
@Table(name = "res_log_mgt_dtl")
@SequenceGenerator(name="id_logMgtDtl",sequenceName = "logMgtDtl_seq",allocationSize = 1)
public class DbResLogMgtDtl extends BaseLogDtl {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_logMgtDtl")
	private Long id;


	
	@Column(name="dtl_skuId")
	private long dtlSkuId;


	@Column(name="dtl_itemId")
	private long dtlItemId;
	
	@Column(name="dtl_repoId")
	private long dtlRepoId;
	
	@Column(name="dtl_qty")
	private long dtlQty;

	@JsonBackReference
	@JoinColumn(name = "log_mgt_id")
	@ManyToOne(targetEntity = DbResLogMgt.class)
	private DbResLogMgt resLogMgt;

}
