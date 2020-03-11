package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pccw.backend.annotation.JsonResultParamMapAnnotation;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * DbResSpec
 */
@Data
@Entity
@Table(name = "res_spec")
@SequenceGenerator(name="id_spec",sequenceName = "spec_seq",allocationSize = 1)
@JsonResultParamMapAnnotation(param1 = "id",param2 = "specName",param3 = "verId")
public class DbResSpec extends Base {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_spec")
	private Long id;

	@Column(name = "spec_name",length = 64)
	private String specName;

	@Column(name="spec_desc",length = 512)
	private String specDesc;

	@Column(name="ver_id",length = 512)
	private String verId;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true) //,orphanRemoval = true  ,fetch=FetchType.EAGER
	@JoinColumn(name = "spec_id")
	private List<DbResSpecAttr> resSpecAttrList;

}
