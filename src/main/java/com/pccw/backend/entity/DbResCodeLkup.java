package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "res_code_lkup")
public class DbResCodeLkup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long id;
	
	@Column(name = "grp_id", length = 40)
	private String grpId;
		
	@Column(name = "code", length = 40)
	private String code;
	
	@Column(name = "description", length = 1000)
	private String description;
	
	@Column(name = "create_by", length = 20)
	private String createBy;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "last_upd_by", length = 20)
	private String lastUpdBy;
	
	@Column(name = "last_upd_date")
	private Date lastUpdDate;
	

}
