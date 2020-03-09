package com.pccw.backend.entity;

import java.util.List;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pccw.backend.annotation.JsonResultParamAnnotation;
import lombok.Data;

/**
 * DbResRole
 */

 @Entity
 @Data
 @Table(name="res_role")
 @SequenceGenerator(name="id_role",sequenceName = "role_seq",allocationSize = 1)
 @JsonResultParamAnnotation(param1 = "id",param2 = "roleName")
public class DbResRole extends Base {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_role")
    private Long id;

    @Column(name="role_name")
    private String roleName;

    @Column(name="role_pid")
    private long rolePid;

    @Column(name="role_desc")
    private String roleDesc;

    @Column(name="role_funtiongroup")
    private String roleFunctionGroup;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "role_id")
    private List<DbResRoleRight> resRoleRightList;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "role_id")
    private List<DbResAccountRole> resAccountRoleList;
    
}
