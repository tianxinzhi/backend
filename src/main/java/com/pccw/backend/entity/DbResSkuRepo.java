package com.pccw.backend.entity;


import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



/*
 * which store, which sku , how many qty
 */


@Entity
@Table(name = "res_sku_repo")
@Getter
@Setter
@SequenceGenerator(name="id_skuRepo",sequenceName = "skuRepo_seq",allocationSize = 1)
public class DbResSkuRepo extends Base{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_skuRepo")
	private Long id;

	@ManyToOne
	@JsonIgnoreProperties(value = { "skuRepoList" })
	@JoinColumn(name = "sku_id")
	private DbResSku sku;

	@ManyToOne
	@JsonIgnoreProperties(value = { "skuRepoList" })
	@JoinColumn(name = "repo_id")
	private DbResRepo repo;
	
	@Column(name = "item_id")
	private Long itemId;

	@ManyToOne
	@JsonIgnoreProperties(value = { "skuRepoList" })
	@JoinColumn(name = "stock_type_id")
	private DbResStockType stockType;

//	@Column(name="subin_id")
//	private String subinId;

	@Column(name = "qty")
	private int qty;

	
}
