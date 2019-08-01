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
@Table(name = "res_repo_transfer_action")
public class DbResRepoTransferAction implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "action_id", length = 22)
	private Long actionId;

	@Column(name = "tx_id", length = 22)
	private Long txId;

	@Column(name = "sku_id", length = 22)
	private Long skuId;

	@Column(name = "item_id", length = 22)
	private Long itemId;

	@Column(name = "action_result_cd", length = 2)
	private String actionResultCd;

	@Column(name = "action_msg", length = 256)
	private String actionMsg;

}
