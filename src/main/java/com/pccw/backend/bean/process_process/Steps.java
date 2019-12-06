package com.pccw.backend.bean.process_process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: ChenShuCheng
 * @create: 2019-12-05 20:36
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Steps {

    private String stepSize;

    private String StepDirection;

    private List<Step> step;
}
