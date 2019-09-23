package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 *
 */

@Data
@Entity
@Table(name = "res_type")
public class DbResType extends Base {

	
	@Id
//	@Column(name = "type_id", length = 11)
	@GeneratedValue		
	private Long id;
	
	@Column(name = "type_code", length = 3)
	private String typeCode;

	@Column(name = "type_name", length = 32)
	private String typeName;

	@Column(name = "sequential",length = 1)
	private String sequential;

	@Column(name = "type_desc", length = 512)
	private String typeDesc;

	@Column(name = "active",length = 1)
	private String active;

//	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//	@JoinTable(name="RES_CLASS_TYPE",
//			joinColumns = { @JoinColumn(name = "TYPE_ID") },
//			inverseJoinColumns = { @JoinColumn(name = "CLASS_ID") }
//	)
//	private List<DbResClass> classList;

}
