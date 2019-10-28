package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import javax.persistence.*;
import java.util.List;


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

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL)  //ALL  PERSIST
	@JoinColumn(name = "log_mgt_id")
    private List<DbResLogMgtDtl> line;

}
