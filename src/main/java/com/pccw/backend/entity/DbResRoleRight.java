package com.pccw.backend.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;



/*
 * which store, which sku , how many qty
 */


@Entity
@Table(name = "res_role_right")
@Data
public class DbResRoleRight extends Base {
	
	@Column(name = "role_id")
	private Long roleId;


	@Column(name = "right_id")
	private Long rightId;
	

	
}
