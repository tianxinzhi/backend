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
@Table(name = "res_stock_request_batch")
public class DbResStockRequestBatch implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "batch_id", length = 16)
	private Long batchId;

	@Column(name = "action", length = 1)
	private String action;

	@Column(name = "sku_num", length = 16)
	private String skuNum;

	@Column(name = "qty", length = 22)
	private int qty;

	@Column(name = "item_value", length = 128)
	private String itemValue;

	@Column(name = "repo_id", length = 16)
	private Long repoId;

	@Column(name = "lot_num", length = 16)
	private String lotNum;

	@Column(name = "remarks", length = 256)
	private String remarks;

	@Column(name = "is_approval", length = 1)
	private String isApproval;

	@Column(name = "status", length = 1)
	private String status;

	@Column(name = "tx_id", length = 22)
	private Long txId;

}
