package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;


/**
 * ROR = RESOURCE ORDER REQUEST
 */
@Data
@Entity
@Table(name = "res_log_mgt_dtl")
@SequenceGenerator(name="id_logMgtDtl",sequenceName = "logMgtDtl_seq",allocationSize = 1)
public class DbResLogMgtDtl extends BaseLogDtl {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_logMgtDtl")
	private Long id;



	@Column(name="dtl_skuId")
	private Long dtlSkuId;


	@Column(name="dtl_itemId")
	private Long dtlItemId;

	@Column(name="dtl_repoId")
	private Long dtlRepoId;

	@Valid
	@Min(1)
	@Column(name="dtl_qty")
	private long dtlQty;

	@Column(name = "item_code",length = 512)
	private String itemCode;

	@Column(name="detail_id")
	private String detailId;

	@JsonBackReference
	@JoinColumn(name = "log_mgt_id")
	@ManyToOne(targetEntity = DbResLogMgt.class)
	private DbResLogMgt resLogMgt;

	@Transient
	private String skuCode;

	@Transient
	private List<DbResSkuRepoSerial> line;
}
