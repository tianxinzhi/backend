package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;


/**
 * repository => store/shop
 */
@Data
@Entity
@Table(name = "res_type_sku_spec")
public class DbResTypeSkuSpec extends Base {


	@Column(name = "type_id",length = 64)
	private long typeId;

	@Column(name="sku_id",length = 512)
	private long skuId;

	@Column(name="spec_id",length = 512)
	private long specId;



}
