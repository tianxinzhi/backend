package com.pccw.backend.entity;

import lombok.Data;
import javax.persistence.*;


/**
 * ROR = RESOURCE ORDER REQUEST
 */
@Data
@Entity
@Table(name = "res_log_ror_dtl")
public class DbResLogRorDtl extends BaseLogDtl {
	
	
	@Column(name="dtl_skuId")
	private long dtlSkuId;


	@Column(name="dtl_itemId")
	private long dtlItemId;
	
	@Column(name="dtl_repoId")
	private long dtlRepoId;
	
	@Column(name="dtl_qty")
	private long dtlQty;

}
