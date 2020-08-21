package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;


/**
 * ROR = RESOURCE ORDER REQUES
 */
@Deprecated
@Data
@Entity
@Table(name = "res_log_repl_dtl")
@SequenceGenerator(name="id_replDtl",sequenceName = "replDtl_seq",allocationSize = 1)
public class DbResLogReplDtl extends BaseLogDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_replDtl")
	private Long id;

	@Column(name="dtl_skuId")
	private long dtlSkuId;


	@Column(name="dtl_itemId")
	private long dtlItemId;

	@Column(name="dtl_repoId")
	private long dtlRepoId;

	@Column(name="dtl_qty")
	private long dtlQty;

	@ManyToOne
	@JoinColumn(name = "db_res_log_repl_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DbResLogRepl dbResLogRepl;

}
