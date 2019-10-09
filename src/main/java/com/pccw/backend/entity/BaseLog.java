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
public class BaseLog extends Base{
       	// Transation Number made from SMP self
	@Column(name="log_txtNum",length = 512)
	private String logTxtBum;
	
	// O - Order / M - Mangement / R - Repl
	@Column(name="log_type",length = 4)
        private String logType; 
        
        // ASG(Assign) / RET(return) / EXC(Exchange) / ARS(Advanced Reserve) / CARS(Cancel advance reserve) / APU(Advance pick up) / RREQ(Replenishment request)/ RREC(Replenishment receive)
	@Column(name = "log_orderNature", length = 8)
        private String logOrderNature;
        
        // W - waiting LIS to handle / D - Done
        @Column(name = "status",length = 4)
        private String status;

        // remark
        @Column(name = "remark",length = 512)
        private String remark;
}