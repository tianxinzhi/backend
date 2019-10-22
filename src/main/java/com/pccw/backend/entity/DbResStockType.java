package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * res_attr_value => product
 */

@Entity
@Table(name = "res_stock_type")
@Data
@SequenceGenerator(name="id_stockType",sequenceName = "stockType_seq",allocationSize = 1)
public class DbResStockType extends Base{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_stockType")
	private Long id;

	@Column(name = "stocktype_name",length = 64)
	private String stockTypeName;


}
