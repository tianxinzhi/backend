package com.pccw.backend.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResTypeDtl;

@Profile("dev1")
public interface ResTypeDTlRepopsitory extends JpaRepository<DbResTypeDtl, Long> {
	
	DbResTypeDtl findBySkuId(Long skuId);
	
	List<DbResTypeDtl> findBySpecId(Long specId);


}
