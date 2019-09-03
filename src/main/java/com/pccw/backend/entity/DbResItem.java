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
	
	
	@Column(name = "item_num", length = 128)
	private String itemNum;
	
	@Column(name = "item_name", length = 32)
	private String itemName;
}
