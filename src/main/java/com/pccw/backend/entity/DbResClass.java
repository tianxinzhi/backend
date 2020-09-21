package com.pccw.backend.entity;

import java.util.List;


import javax.persistence.*;

import com.pccw.backend.annotation.JsonResultParamMapAnnotation;
import com.pccw.backend.annotation.JsonResultParamMapPro;
import lombok.Getter;
import lombok.Setter;


/**
 * class => category of sku
 */

@Getter
@Setter
@Entity
@Table(name = "res_class")
@SequenceGenerator(name="id_class",sequenceName = "class_seq",allocationSize = 1)
//@JsonResultParamMapAnnotation(param1 = "id",param2 = "parentClassId",param3 = "className")
@JsonResultParamMapPro(fieldMapping = {"id=id","pid=parentClassId","name=className"})
public class  DbResClass extends Base {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_class")
	private Long id;



	@Column(name = "parent_class_id", length = 11)
	private String parentClassId;

	@Column(name = "class_name", length = 32)
	private String className;

	@Column(name = "class_type", length = 128)
	private String classType;

	@Column(name = "class_desc", length = 128)
	private String classDesc;

//	@JsonIgnore
//	@JsonIgnoreProperties(value = { "classList" })
//	@ManyToMany(mappedBy = "classList")
//	private List<DbResType> typeList;

	@OneToMany(cascade={CascadeType.ALL},mappedBy = "classs",orphanRemoval = true)
	List<DbResClassType> relationOfTypeClass;


}
