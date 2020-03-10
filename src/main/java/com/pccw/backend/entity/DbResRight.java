package com.pccw.backend.entity;


import javax.persistence.*;
import javax.persistence.Entity;

import com.pccw.backend.annotation.JsonResultParamAnnotation;
import lombok.Data;

import javax.persistence.*;

/**
 * DbResRight
 */

 @Entity
 @Data
 @Table(name = "res_right")
 @SequenceGenerator(name="id_right",sequenceName = "right_seq",allocationSize = 1)
 @JsonResultParamAnnotation(param1 = "id",param2 = "rightPid",param3 = "rightName")
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

    /**
     * 权限标识
     * 菜单 ——> 类名
     * 按钮 ——> 方法路由
     */
    @Column(name = "right_identifier")
    private String rightIdentifier;

    /**
     * 显示排序
     */
    @Column(name = "sort_num")
    private Long sortNum;
}