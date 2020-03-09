package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


/**
 * class => category of sku
 */

@Getter
@Setter
@Entity
@Table(name = "res_class_type")
@SequenceGenerator(name="id_class_type",sequenceName = "class_type_seq",allocationSize = 1)
public class DbResClassType extends Base {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_class_type")
	private Long id;

	@ManyToOne
	@JsonIgnoreProperties(value = { "relationOfTypeClass" })
	@JoinColumn(name = "class_id")
	private DbResClass classs;

	@ManyToOne
	@JsonIgnoreProperties(value = { "relationOfTypeClass" })
	@JoinColumn(name = "type_id")
	private DbResType type;
	

}
