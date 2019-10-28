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
public class DbResLogMgtDtl extends BaseLogDtl {
	


	
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
	private DbResLogMgt logMgt;

}
