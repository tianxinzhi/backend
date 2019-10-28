package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


/**
 * repository => store/shop
 */
@Data
@Entity
@Table(name = "res_type_sku_spec")
@SequenceGenerator(name="id_tsp",sequenceName = "tsp_seq",allocationSize = 1)
public class DbResTypeSkuSpec extends Base {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_tsp")
	private Long id;

//	@Column(name = "type_id",length = 64)
//	private long typeId;

	@Column(name="sku_id",length = 512)
	private long skuId;

	@Column(name="spec_id",length = 512)
	private long specId;

	@OneToOne
	@JsonBackReference
	@JoinColumn(name = "type_id")
	private DbResType type;





}
