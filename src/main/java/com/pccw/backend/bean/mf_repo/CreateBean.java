package com.pccw.backend.bean.mf_repo;


import com.pccw.backend.bean.BaseBean;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SearchCondition
 */

 @Data
@NoArgsConstructor
public class CreateBean extends BaseBean {

    private String repoCode;

    private String repoName;

    private String repoAddr;

    private Long areaId;
    
}