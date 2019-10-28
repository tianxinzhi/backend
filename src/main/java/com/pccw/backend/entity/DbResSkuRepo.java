package com.pccw.backend.entity;


import javax.persistence.*;


import lombok.Data;



/*
 * which store, which sku , how many qty
 */


@Entity
@Table(name = "res_sku_repo")
@Data
@SequenceGenerator(name="id_skuRepo",sequenceName = "skuRepo_seq",allocationSize = 1)
public class DbResSkuRepo extends Base{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_skuRepo")
	private Long id;
	
	@Column(name = "sku_id")
	private Long skuId;

	@Column(name = "repo_id")
	private Long repoId;
	
	@Column(name = "item_id")
	private Long itemId;

	@Column(name="subin_id")
	private String subinId;
	

	@Column(name = "qty")
	private int qty;

	
}
