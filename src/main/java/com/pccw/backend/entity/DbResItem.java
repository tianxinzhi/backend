package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;


/**
 *  one sku can include many items
 */

@Data
@Entity
@Table(name = "res_item")
@Deprecated
@SequenceGenerator(name="id_item",sequenceName = "item_seq",allocationSize = 1)
public class DbResItem extends Base {


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_item")
	private Long id;


	@Column(name = "item_code", length = 128)
	private String itemCode;

	@Column(name = "item_name", length = 32)
	private String itemName;

	// A - avaliable, S - saled
	@Column(name = "item_status", length = 4)
	private String itemStatus;
}
