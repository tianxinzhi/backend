package com.pccw.backend.entity;

import lombok.Data;
import javax.persistence.*;


/**
 * ROR = RESOURCE ORDER REQUEST
 */
@Data
@Entity
@Table(name = "res_log_mgt")
@SequenceGenerator(name="id_logMgt",sequenceName = "logMgt_seq",allocationSize = 1)
public class DbResLogMgt extends BaseLog {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_logMgt")
	private Long id;



	// Repo In
	@Column(name="log_repo_in")
	private long logRepoIn;
	
	// Repo Out
	@Column(name="log_repo_out")
	private long logRepoOut;



}
