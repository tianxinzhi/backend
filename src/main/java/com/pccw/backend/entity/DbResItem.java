package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "res_item")
public class DbResItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	@Column(name = "sku_id")
	private Long skuId;
	
	@Column(name = "key_item_value", length = 128)
	private String keyItemValue;
	
	@Column(name = "status", length = 3)
	private String status;
	
	@Column(name = "lot_num", length = 16)
	private String lotNum;
	
	@Column(name = "eff_start_date")
	private Date effStartDate;
	
	@Column(name = "eff_end_date")
	private Date effEndDate;

}
