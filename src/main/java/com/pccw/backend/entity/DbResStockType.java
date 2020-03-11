package com.pccw.backend.entity;

import com.pccw.backend.annotation.JsonResultParamMapAnnotation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


/**
 * res_attr_value => product
 */
@Getter
@Setter
@Entity
@Table(name = "res_stock_type")
@SequenceGenerator(name="id_stockType",sequenceName = "stockType_seq",allocationSize = 1)
@JsonResultParamMapAnnotation(param1 = "id",param2 = "stockTypeName")
public class DbResStockType extends Base{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_stockType")
	private Long id;

	@Column(name = "stocktype_name",length = 64)
	private String stockTypeName;

	@OneToMany(cascade={CascadeType.ALL},mappedBy = "stockType")
	private List<DbResSkuRepo> skuRepoList;


}
