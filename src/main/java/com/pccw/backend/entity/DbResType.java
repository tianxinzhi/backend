package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 *
 */

@Data
@Entity
@Table(name = "RES_TYPE")
public class DbResType extends Base {

	
	@Id
//	@Column(name = "TYPE_ID", length = 11)
	@GeneratedValue		
	private Long id;
	
	@Column(name = "TYPE_CODE", length = 3)
	private String typeCode;

	@Column(name = "TYPE_NAME", length = 32)
	private String typeName;

	@Column(name = "SEQUENTIAL",length = 1)
	private String sequential;

	@Column(name = "TYPE_DESC", length = 512)
	private String typeDesc;

	@Column(name = "ACTIVE",length = 1)
	private String active;

//	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//	@JoinTable(name="RES_CLASS_TYPE",
//			joinColumns = { @JoinColumn(name = "TYPE_ID") },
//			inverseJoinColumns = { @JoinColumn(name = "CLASS_ID") }
//	)
//	private List<DbResClass> classList;

}
