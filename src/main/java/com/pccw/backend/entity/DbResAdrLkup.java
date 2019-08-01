package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "res_adr_lkup")
public class DbResAdrLkup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="res_area_id", referencedColumnName="id")
	private DbResArea area;

	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="res_repo_id", referencedColumnName="id")
	private DbResRepo repo;

	@Column(name = "adr_area_type", length = 16)
	private String adrAreaType;

	@Column(name = "adr_area_id", length = 16)
	private Long adrAreaId;
	
	@Column(name = "eff_start_date", length = 16)
	private Date effStartDate;

	@Column(name = "eff_end_date", length = 16)
	private Date effEndDate;

}
