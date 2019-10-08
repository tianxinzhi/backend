package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * DbResFlow
 */

 @Entity
 @Data
 @Table(name = "res_flow")
public class DbResFlow extends Base{



    @Column(name="flow_name")
    private String flowName;

    
}