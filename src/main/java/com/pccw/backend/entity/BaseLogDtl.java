package com.pccw.backend.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.NonNull;

/**
 * Base
 */

@Data
@MappedSuperclass
public class BaseLogDtl extends Base {
	// Transation Number made from SMP self
	@Column(name = "log_txtNum", length = 512)
	private String logTxtBum;

	@Column(name = "dtl_logId")
	private long dtlLogId;

	// A - Add / D - Deduct
	@Column(name = "dtl_action", length = 4)
	private String dtlAction;

	// Good / Faulty / Intran
	@Column(name = "dtl_subin", length = 8)
	private String dtlSubin;

	// W - waiting LIS to handle / D - Done
	@Column(name = "lis_status", length = 4)
	private String lisStatus;

	// result from LIS
	@Column(name = "lis_result", length = 512)
	private String lisResult;

	// AVL(available)/DEM(demo)/RES(reserve)/ARE(ao_reserve)
	// FAU(faulty) 
	// INT(intransit) 
	@Column(name = "status", length = 4)
	private String Status;
}