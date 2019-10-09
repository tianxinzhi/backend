package com.pccw.backend.entity;

import lombok.Data;
import javax.persistence.*;


/**
 * ROR = RESOURCE ORDER REQUEST
 */
@Data
@Entity
@Table(name = "res_log_mgt")
public class DbResLogMgt extends BaseLog {
	



	// Repo In
	@Column(name="log_repo_in")
	private long logRepoIn;
	
	// Repo Out
	@Column(name="log_repo_out")
	private long logRepoOut;



}
