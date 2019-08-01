package com.pccw.backend.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResSpecAttr;

@Profile("dev1")
public interface ResSpecAttrRepository extends JpaRepository<DbResSpecAttr, Long>{
	
	List <DbResSpecAttr> findBySpecId(Long specId);
	
	List<DbResSpecAttr> findByAttrId(Long attrId);
	
	List<DbResSpecAttr> findByAttrValueId(Long attrValueId);

}
