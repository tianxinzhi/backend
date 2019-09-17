package com.pccw.backend.bean;
/**
 * KV
 */


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
// @Builder
// @NoArgsConstructor
@AllArgsConstructor
public class KV extends BaseBean {
	private String k;
    private Long v;
}