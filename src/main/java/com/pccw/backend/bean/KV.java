package com.pccw.backend.bean;
/**
 * KV
 */


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// @Builder
// @NoArgsConstructor
@AllArgsConstructor
public class KV extends BaseBean {
	private String k;
    private Long v;
}