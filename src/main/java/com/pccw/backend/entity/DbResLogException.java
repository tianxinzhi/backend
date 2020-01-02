package com.pccw.backend.entity;


import com.pccw.backend.annotation.JsonResultParamHandle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 *
 */

@Data
@Entity
@Table(name = "res_log_exception")
@SequenceGenerator(name="id_log_exception",sequenceName = "log_exception_seq",allocationSize = 1)
@AllArgsConstructor
public class DbResLogException {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_log_exception")
	private Long id;

	@Column(name = "log_exception_code",length = 32)
	private String code;

	@Column(name = "log_exception_msg",length = 512)
	private String msg;
	
//	@Column(name = "log_exception_fileName", length = 64)
//	private String fileName;

//	@Column(name = "log_exception_methodName", length = 64)
//	private String methodName;

	@Column(name = "log_exception_reqUri", length = 64)
	private String reqUri;

	@Column(name="create_at")
	private Long createAt;

	@Column(name="create_by")
	private Long createBy;
}
