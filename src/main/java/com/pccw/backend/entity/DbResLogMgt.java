package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


/**
 * ROR = RESOURCE ORDER REQUEST
 */
@Getter
@Setter
@Entity
@Table(name = "res_log_mgt")
@SequenceGenerator(name="id_logMgt",sequenceName = "logMgt_seq",allocationSize = 1)
public class DbResLogMgt extends BaseLog {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_logMgt")
	private Long id;

	@Column(name = "adjust_reason_id")
	private long adjustReasonId;

	@Column(name = "delivery_date")
	private long deliveryDate;

	@Column(name = "delivery_status")
	private String deliveryStatus;

    @Column(name = "delivery_number")
    private String deliveryNumber;

	@Column(name = "staff_number")
	private String staffNumber;

	@Column(name = "source_system")
	private String sourceSystem;

	@Column(name = "source_txn_header")
	private String sourceTxnHeader;

	@Column(name = "source_line")
	private String sourceTxnLine;

	// OrderId
	@Column(name="log_orderId",length = 512)
	private String logOrderId;

	// Related OrderId
	@Column(name="log_relatedOrderId",length = 512)
	private String logRelatedOrderId;

	// N(normal) / A(Advance Order)
	@Column(name="log_orderType",length = 1)
	private String logOrderType;

	@Column(name="tx_date",length = 20)
	private String txDate;

	@Column(name = "mobile_number")
	private String mobileNumber;

	// Repo In
	@Column(name="log_repo_in")
	private long logRepoIn;

	// Repo Out
	@Column(name="log_repo_out")
	private long logRepoOut;

	@Column(name="reservation_id")
	private Long reservationId;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL)  //ALL  PERSIST
	@JoinColumn(name = "log_mgt_id")
    private List<DbResLogMgtDtl> line;

}
