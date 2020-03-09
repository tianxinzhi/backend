package com.pccw.backend.entity;


import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "res_role_right")
@Data
@SequenceGenerator(name="id_roleRight",sequenceName = "roleRight_seq",allocationSize = 1)
public class DbResRoleRight extends Base {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_roleRight")
	private Long id;

	@Column(name = "right_id")
	private Long rightId;
	

	
}
