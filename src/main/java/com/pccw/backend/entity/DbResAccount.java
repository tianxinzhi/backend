package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;


/**
 *  one area may have many reposity/shop/store
 */

@Data
@Entity
@Table(name = "res_account")
public class DbResAccount extends Base {

	@Id
	@GeneratedValue
	@Column(columnDefinition = "number(11)")
	private Long id;

	@Column(name = "role_id", columnDefinition = "number(11)")
	private Long roleId;

	@Column(name = "account_name", columnDefinition = "varchar(100)")
	private String accountName;
	
	@Column(name = "password", columnDefinition = "varchar(255)")
	private String password;
	
	// @Column(name = "status", length = 6)	
	// private String status;

}
