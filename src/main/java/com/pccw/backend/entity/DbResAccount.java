package com.pccw.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * DbResAccount
 */
@Entity
@Data
@Table(name="res_account")
public class DbResAccount extends Base{

    @Id
    @GeneratedValue
    private long id;

    @Column(name="account_name")
    private String accountName;

    @Column(name="role_id")
    private long roleId;
}