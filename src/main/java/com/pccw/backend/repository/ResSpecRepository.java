package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResSpec;

@Profile("dev1")
public interface ResSpecRepository extends JpaRepository<DbResSpec, Long> {
	
	DbResSpec findBySpecName(String specName);

}
