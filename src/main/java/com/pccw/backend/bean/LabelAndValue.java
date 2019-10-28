package com.pccw.backend.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LabelAndValue {
    private Object Value;
    private Object Label;
    private Object Other;
}
