package com.pccw.backend.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


import lombok.Data;

/**
 * DbResRole
 */

 @Entity
 @Data
 @Table(name="res_role")
public class DbResRole implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(name="role_name")
    private String roleName;

    @Column(name="role_desc")
    private String roleDesc;
    
    @OneToMany(fetch=FetchType.EAGER)
	@JoinTable(name="res_role_right",joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="id")})
    private List<DbResRight> rightList;
}