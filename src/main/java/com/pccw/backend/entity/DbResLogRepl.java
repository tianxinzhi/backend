package com.pccw.backend.entity;
import lombok.Data;
import javax.persistence.*;
import java.util.List;


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

	//warehouseID
	@Column(name="repo_id_from")
	private long repoIdFrom;

	//shop ID
	@Column(name="repo_id_to")
	private long repoIdTo;

	@OneToMany(cascade={CascadeType.ALL},mappedBy="dbResLogRepl")
	private List<DbResLogReplDtl> line;



}
