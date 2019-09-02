package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


import lombok.Data;



/*
 * which store, which sku , how many qty
 */


@Entity
@Table(name = "res_role_right")
@Data
public class DbResRoleRight implements Serializable {

	/**
	 *
	 */
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue	
	private Long id;
	
	@Column(name = "role_id")
	private Long roleId;


	@Column(name = "right_id")
	private Long rightId;
	

	
}
