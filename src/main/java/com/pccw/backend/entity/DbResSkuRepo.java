package com.pccw.backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

/*
 * which store, which sku , how many qty
 */


@Entity
@Table(name = "res_sku_repo")
@Getter
@Setter
@SequenceGenerator(name="id_skuRepo",sequenceName = "skuRepo_seq",allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
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

//	@OneToMany(cascade={CascadeType.ALL},mappedBy = "skuRepo",orphanRemoval = true)
//	private List<DbResSkuRepoItem> skuRepoItemList;

//	@Column(name="subin_id")
//	private String subinId;

	@Column(name = "qty")
	private Long qty;

	// remark
	@Column(name = "remark",length = 512)
	private String remark;

	@Column(name = "date_received")
	private Long dateReceived;

	@Column(name = "is_consigned",length = 8)
	private String isConsigned;

	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "skuRepo",orphanRemoval = true)
	private List<DbResSkuRepoSerial> serials;

	public DbResSkuRepo(Object o, DbResSku dbResSku, DbResRepo dbResRepo, Object o1, DbResStockType dbResStockType, Object o2, long parseInt, Object o3) {
	}
}
