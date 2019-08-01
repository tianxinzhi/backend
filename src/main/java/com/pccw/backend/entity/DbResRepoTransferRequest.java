package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "res_repo_transfer_request")
public class DbResRepoTransferRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "tx_id", length = 22)
	private Long txId;

	@Column(name = "trans_level", length = 1)
	private String transLevel;

	@Column(name = "res_id", length = 22)
	private Long resId;

	@Column(name = "from_repo_id", length = 22)
	private Long fromRepoId;

	@Column(name = "to_repo_id", length = 22)
	private Long toRepoId;

	@Column(name = "qty", length = 22)
	private int qty;

	@Column(name = "remarks", length = 256)
	private String remarks;

	@Column(name = "approval_status", length = 3)
	private String approvalStatus;

	@Column(name = "return_cd", length = 2)
	private String returnCd;

	@Column(name = "return_msg", length = 256)
	private String returnMsg;

}
