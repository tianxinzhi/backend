package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;


/*
 * which store, which sku , how many qty
 */


@Entity
@Table(name = "res_item_repo")

public class DbResItemRepo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue	
	public Long id;
	
	@Column(name = "item_id")
	public Long itemId;

	@Column(name="item_num")
	public String itemNum;
	
	// public String getItemNum(){
	// 	return itemNum;
	// }
	@Column(name = "sku_id")
	public Long skuId;

	@Column(name="sku_num")
	public String skuNum;

	@Column(name = "repo_id")
	public Long repoId;

	@Column(name="repo_num")
	public String repoNum;
	
	@Column(name = "qty")
	public int qty;
	
	@Column(name = "status", length = 3)
	public String status;

	
}
