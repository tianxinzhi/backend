package com.pccw.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


/**
 * class => category of sku
 */

@Getter
@Setter
@Entity
@Table(name = "res_class_lis")
@SequenceGenerator(name="id_classLis",sequenceName = "classLis_seq",allocationSize = 1)
//@JsonResultParamHandle(param1 = "id",param2 = "parentClassId",param3 = "className")
public class DbResClassLis extends Base {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_classLis")
	private Long id;

	@Column(name = "class_desc", length = 128)
	private String classDesc;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "class_id",referencedColumnName = "id")
	private DbResClass classId;//外键

//	@JsonBackReference
//	@OneToMany(cascade={CascadeType.ALL},mappedBy = "classLisId",orphanRemoval = true)
//	List<DbResSkuAttrValueLis> attrValueLisList;
	

}
