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
public class TreeSelect extends BaseBean {
	private Long key;
    private Long value;
    private String title;
}