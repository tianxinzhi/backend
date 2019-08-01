package com.pccw.backend.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResAttrValueAssgn;

@Profile("dev1")
public interface ResAttrValueAssignRepository extends JpaRepository <DbResAttrValueAssgn, Long>{
	
	List<DbResAttrValueAssgn> findByAttrId(Long id);
	
	DbResAttrValueAssgn findByAttrValueId(Long attrValueId);
	
//	@Transactional
//	@Modifying
//	@Query(value = "UPDATE res_attr_value_assgn u set eff_start_date = :effStartDate, eff_end_date = :effEndDate where u.attr_value_id = :attrValueId", nativeQuery = true)
//	void updateResAttrValueAssgn(@Param("effStartDate") Date effStartDate, @Param("effEndDate") Date effEndDate, @Param("attrValueId") Long attrValueId);	

}
