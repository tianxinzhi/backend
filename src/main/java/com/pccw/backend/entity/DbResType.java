package com.pccw.backend.entity;


import com.pccw.backend.annotation.JsonResultParamHandle;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 *
 */

@Getter
@Setter
@Entity
@Table(name = "res_type")
@SequenceGenerator(name="id_type",sequenceName = "type_seq",allocationSize = 1)
@JsonResultParamHandle(param1 = "id",param2 = "typeName")
public class DbResType extends Base {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_type")
	private Long id;
	
	
	@Column(name = "type_code", length = 32)
	private String typeCode;

	@Column(name = "type_name", length = 32)
	private String typeName;

	@Column(name = "sequential",length = 32)
	private String sequential;

	@Column(name = "type_desc", length = 512)
	private String typeDesc;


//	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//	@JoinTable(name="res_class_type",
//			joinColumns = { @JoinColumn(name = "type_id") },
//			inverseJoinColumns = { @JoinColumn(name = "class_id") }
//	)
//	private List<DbResClass> classList;

	@OneToMany(cascade={CascadeType.ALL},mappedBy = "type",orphanRemoval = true)
	private List<DbResClassType> relationOfTypeClass;

	@OneToMany(cascade={CascadeType.ALL},mappedBy = "type",orphanRemoval = true)
	private List<DbResTypeSkuSpec> dbResTypeSkuSpecList;


}
