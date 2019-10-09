package com.pccw.backend.entity;

import java.util.Set;

import javax.persistence.*;


import lombok.Data;

/**
 * DbResRole
 */

 @Entity
 @Data
 @Table(name="res_role")
public class DbResRole extends Base {

    @Column(name="role_name")
    private String roleName;

    @Column(name="role_pid")
    private long rolePid;

    @Column(name="role_desc")
    private String roleDesc;

    @Column(name="role_funtiongroup")
    private String roleFunctionGroup;
    

    @ManyToMany(fetch = FetchType.EAGER)
        //  @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "res_role_right", 
    joinColumns = { @JoinColumn(name = "role_id") },
    inverseJoinColumns = { @JoinColumn(name = "right_id") })
    private Set<DbResRight> rightList;

    
}