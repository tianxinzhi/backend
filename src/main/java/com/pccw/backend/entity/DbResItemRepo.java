package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


/**
 * which store, which sku , how many qty
 */


@Data
@Entity
@Table(name = "res_item_repo")
public class DbResItemRepo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	@Column(name = "item_id")
	private Long itemId;

	@Column(name="item_num")
	private String itemNum;
	
	@Column(name = "sku_id")
	private Long skuId;

	@Column(name="sku_num")
	private String skuNum;

	@Column(name = "repo_id")
	private Long repoId;

	@Column(name="repo_num")
	private String repoNuml;
	
	@Column(name = "qty")
	private int qty;
	
	@Column(name = "status", length = 3)
	private String status;

	
}
