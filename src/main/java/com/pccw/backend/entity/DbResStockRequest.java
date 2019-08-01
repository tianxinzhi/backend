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
@Table(name = "res_stock_request")
public class DbResStockRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "tx_id", length = 22)
	private Long txId;

	@Column(name = "action", length = 1)
	private String action;

	@Column(name = "sku_id", length = 22)
	private Long skuId;

	@Column(name = "qty", length = 22)
	private int qty;

	@Column(name = "item_id", length = 22)
	private Long itemId;

	@Column(name = "item_value", length = 128)
	private String itemValue;

	@Column(name = "repo_id", length = 22)
	private Long repoId;

	@Column(name = "lot_num", length = 16)
	private String lotNum;

	@Column(name = "item_status", length = 3)
	private String itemStatus;

	@Column(name = "remarks", length = 256)
	private String remarks;

	@Column(name = "approval_status", length = 3)
	private String approvalStatus;

	@Column(name = "req_form", length = 16)
	private String reqForm;

	@Column(name = "return_cd", length = 2)
	private String returnCd;

	@Column(name = "return_msg", length = 256)
	private String returnMsg;

}
