package com.pccw.backend.entity;


import javax.persistence.*;
import javax.persistence.Entity;

import com.pccw.backend.annotation.JsonResultParamHandle;
import lombok.Data;

/**
 * DbResRight
 */

 @Entity
 @Data
 @Table(name = "res_right")
 @SequenceGenerator(name="id_right",sequenceName = "right_seq",allocationSize = 1)
 @JsonResultParamHandle(param1 = "id",param2 = "rightPid",param3 = "rightName")
public class DbResRight extends Base{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_right")
    private Long id;

    @Column(name="right_pid")
    private Long rightPid;

    @Column(name="right_name")
    private String rightName;

    @Column(name="right_url")
    private String rightUrl;

    /**
     * List 目录
     * Menu 菜单
     * Button 按钮
     */
    @Column(name = "right_type")
    private String rightType;
    
}