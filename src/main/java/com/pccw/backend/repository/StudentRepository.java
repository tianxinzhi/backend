package com.pccw.backend.repository;


import com.pccw.backend.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student,Integer> {
    
}