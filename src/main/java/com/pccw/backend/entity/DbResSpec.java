package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;


/**
 * repository => store/shop
 */
@Data
@Entity
@Table(name = "res_spec")
@SequenceGenerator(name="id_spec",sequenceName = "spec_seq",allocationSize = 1)
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

	/*@ManyToMany(cascade =CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinTable(name = "res_spec_attr",
			joinColumns = { @JoinColumn(name = "spec_id", referencedColumnName = "id") },
			inverseJoinColumns = { @JoinColumn(name = "attr_value_id", referencedColumnName = "id") })
	    private List<DbResAttrValue> resSpecAttrList;*/


	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true) //,orphanRemoval = true  ,fetch=FetchType.EAGER
	@JoinColumn(name = "spec_id")
	private List<DbResSpecAttr> resSpecAttrList;

}
