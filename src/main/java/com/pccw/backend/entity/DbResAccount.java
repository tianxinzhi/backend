package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;


/**
 *  one area may have many reposity/shop/store
 */

@Data
@Entity
@Table(name = "res_account")
@SequenceGenerator(name="id_account",sequenceName = "account_seq",allocationSize = 1)
public class DbResAccount extends Base {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_account")
	private Long id;
	// @Id
	// @GeneratedValue
	// @Column(columnDefinition = "number(11)")
	// private Long id;

	@Column(name = "role_id", columnDefinition = "number(11)")
	private Long roleId;

	@Column(name = "account_name", columnDefinition = "varchar(100)")
	private String accountName;
	
	@Column(name = "account_password", columnDefinition = "varchar(255)")
	private String accountPassword;
	
	// @Column(name = "status", length = 6)	
	// private String status;

}
