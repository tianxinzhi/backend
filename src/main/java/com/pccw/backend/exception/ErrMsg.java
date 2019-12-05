package com.pccw.backend.exception;
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
public class ErrMsg {
	private String code;
    private String msg;
}