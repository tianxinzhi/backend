package com.pccw.backend.entity;

import java.util.List;


import javax.persistence.*;

import lombok.Data;



/**
 * class => category of sku
 */

@Data
@Entity
@Table(name = "RES_CLASS")
public class  DbResClass extends Base {

	
	@Id
//	@Column(name = "CLASS_ID", length = 11)
	@GeneratedValue		
	private Integer id;

	@Column(name = "PARENT_CLASS_ID", length = 11)
	private String parentClassId;
	
	@Column(name = "CLASS_NAME", length = 32)
	private String className;

	@Column(name = "CLASS_TYPE", length = 128)
	private String classType;

	@Column(name = "CLASS_DESC", length = 128)
	private String classDesc;

	@Column(name = "ACTIVE", length = 1)
	private String active;

//	@ManyToMany(mappedBy = "classList")
//	private List<DbResType> typeList;
	

}
