package com.pccw.backend.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;




/**
 *  one sku can include many items
 */

@Data
@Entity
@Table(name = "res_item")
public class DbResItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue		
	private Long id;
	
	@Column(name = "sku_id")
	private Long skuId;
	
	@Column(name = "item_num", length = 128)
	private String itemNum;
	
	@Column(name = "status", length = 3)
	private String status;
	
	@Column(name = "lot_num", length = 16)
	private String lotNum;

}
