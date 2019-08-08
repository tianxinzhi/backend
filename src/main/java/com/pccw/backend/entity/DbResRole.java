package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
}