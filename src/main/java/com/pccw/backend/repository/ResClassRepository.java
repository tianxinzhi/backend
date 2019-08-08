package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResClass;


public interface ResClassRepository extends JpaRepository<DbResClass, Long> {
	
	// DbResClass findByClassName(String className);

}
