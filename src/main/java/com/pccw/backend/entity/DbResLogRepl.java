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
@SequenceGenerator(name="id_repl",sequenceName = "repl_seq",allocationSize = 1)
public class DbResLogRepl extends BaseLog {


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_repl")
	private Long id;

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
