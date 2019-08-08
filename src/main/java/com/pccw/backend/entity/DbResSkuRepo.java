package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


import lombok.Data;



/*
 * which store, which sku , how many qty
 */


@Entity
@Table(name = "res_sku_repo")
@Data
public class DbResSkuRepo implements Serializable {

	/**
	 *
	 */
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue	
	private Long id;
	
	// @Column(name = "item_id")
	// private Long itemId;

	// @Column(name="item_num")
	// private String itemNum;


	// @OneToOne
	// @JoinColumn(name = "itemNum")
    // private DbResItem item; 
	
	@Column(name = "sku_id")
	private Long skuId;

	@Column(name="sku_num")
	private String skuNum;

	// @OneToOne
	// private DbResSku sku;

	@Column(name = "repo_id")
	private Long repoId;

	@Column(name="repo_num")
	private String repoNum;
	

	@Column(name = "qty")
	private int qty;
	
	@Column(name = "status", length = 3)
	private String status;

	
}
