package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


/**
 * ROR = RESOURCE ORDER REQUEST
 */
@Getter
@Setter
@Entity
@Table(name = "res_log_mgt")
@SequenceGenerator(name="id_logMgt",sequenceName = "logMgt_seq",allocationSize = 1)
public class DbResLogMgt extends BaseLog {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_logMgt")
	private Long id;

	@Column(name = "adjust_reason_id")
	private long adjustReasonId;

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
