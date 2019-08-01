package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResAttr;

@Profile("dev1")
public interface ResAttrRepository extends JpaRepository<DbResAttr, Long> {

	DbResAttr findByAttrName(String attrName);
	

}
