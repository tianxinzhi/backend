package com.pccw.backend.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

/**
 * DbResRole
 */

 @Entity
 @Data
 @Table(name="res_role")
 @SequenceGenerator(name="id_role",sequenceName = "role_seq",allocationSize = 1)
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
    
}