package com.pccw.backend.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResSku;

@Profile("dev1")
public interface ResSkuRepository extends JpaRepository<DbResSku, Long> {

	DbResSku findBySkuNum(String skuNum);
	
	List<DbResSku> findByTypeId(Long typeId);

}
