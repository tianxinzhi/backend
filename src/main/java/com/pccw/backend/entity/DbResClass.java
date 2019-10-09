package com.pccw.backend.entity;

import java.util.List;


import javax.persistence.*;

import lombok.Data;



/**
 * class => category of sku
 */

@Data
@Entity
@Table(name = "res_class")
public class  DbResClass extends Base {

	


	@Column(name = "parent_class_id", length = 11)
	private String parentClassId;
	
	@Column(name = "class_name", length = 32)
	private String className;

	@Column(name = "class_type", length = 128)
	private String classType;

	@Column(name = "class_desc", length = 128)
	private String classDesc;


//	@ManyToMany(mappedBy = "classList")
//	private List<DbResType> typeList;
	

}
