package com.pccw.backend.entity;

import lombok.Data;
import javax.persistence.*;


/**
 * ROR = RESOURCE ORDER REQUEST
 */
@Data
@Entity
@Table(name = "res_log_repl")
public class DbResLogRepl extends BaseLog {
	



	// BatchId
	@Column(name="log_batchId")
	private long logBatchId;
	
	// DN Num
	@Column(name="log_dnNum")
	private long logDNNum;



}
