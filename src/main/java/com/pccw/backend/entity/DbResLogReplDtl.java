package com.pccw.backend.entity;

import lombok.Data;
import javax.persistence.*;


/**
 * ROR = RESOURCE ORDER REQUES
 */
@Data
@Entity
@Table(name = "res_log_repl_dtl")
public class DbResLogReplDtl extends BaseLogDtl {
	
	
	@Column(name="dtl_skuId")
	private long dtlSkuId;


	@Column(name="dtl_itemId")
	private long dtlItemId;
	
	@Column(name="dtl_repoId")
	private long dtlRepoId;
	
	@Column(name="dtl_qty")
	private long dtlQty;

	@ManyToOne
	@JoinColumn(name = "db_res_log_repl_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DbResLogRepl dbResLogRepl;

}
