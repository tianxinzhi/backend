package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;


/**
 *  one area may have many reposity/shop/store
 */

@Data
@Entity
@Table(name = "res_account_role")
@SequenceGenerator(name="id_account_role",sequenceName = "accountRole_seq",allocationSize = 1)
public class DbResAccountRole extends Base {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_account_role")
	private Long id;

	@Column(name = "role_id")
	private Long roleId;

//	@ManyToOne
//	@JsonBackReference
//	@JoinColumn(name = "account_id")
//	private DbResAccount account;
	
}
