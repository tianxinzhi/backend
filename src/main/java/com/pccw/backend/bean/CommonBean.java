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
public class CommonBean extends BaseBean {
	private Long id;
    private Long pid;
    private String name;
}