package com.pccw.backend.entity;

import lombok.Data;
import javax.persistence.*;


/**
 * ROR = RESOURCE ORDER REQUEST
 */
@Data
@Entity
@Table(name = "res_log_ror")
public class DbResLogROR extends Base {
	

	// Transation Number made from SMP self
	@Column(name="ror_txtNum",length = 512)
	private String rorTxtBum;

	// POS / BOM / BES
	@Column(name = "ror_sys",length = 64)
	private String rorSys;

	@Column(name="ror_orderId",length = 512)
	private String rorOrderId;
	
	@Column(name="ror_relatedOrderId",length = 512)
	private String rorRelatedOrderId;

	// N(normal) / A(Advance Order)
	@Column(name="ror_orderType",length = 1)
	private String rorOrderType;

	// ASG(Assign) / RET(return) / EXC(Exchange) / ARS(Advanced Reserve) / CARS(Cancel advance reserve) / APU(Advance pick up)
	@Column(name = "ror_orderNature", length = 8)
	private String rorOrderNature;


}
