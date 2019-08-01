package com.pccw.backend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


/**
 * Student
 */
@Entity
@Data
@Table(name="student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Column(name="name")
    private String name;

    public Student(String name) {
        super();
        this.name=name;
        // this.classId=classId;
    }
    public Student() {
        super();
    }
    @ManyToOne
    @JoinColumn(name="category_Id",referencedColumnName = "id")
    private Category catgory;
}