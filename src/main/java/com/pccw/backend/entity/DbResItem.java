package com.pccw.backend.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;




/**
 *  one sku can include many items
 */

@Entity
@Table(name = "res_item")
public class DbResItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue		
	public Long id;
	
	@Column(name = "sku_id")
	public Long skuId;
	
	@Column(name = "item_num", length = 128)
	public String itemNum;
	
	@Column(name = "status", length = 3)
	public String status;
	
	@Column(name = "lot_num", length = 16)
	public String lotNum;

}
