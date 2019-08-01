package com.pccw.backend.repository;


import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResAttrGrpMember;

@Profile("dev1")
public interface ResAttrGrpMemberRepository extends JpaRepository<DbResAttrGrpMember, Long> {

	DbResAttrGrpMember findByAttrGrpId(Long attrGrpId);
	
	DbResAttrGrpMember findByAttrId(Long attrId);
	
//	@Transactional
//	@Modifying
//	@Query(value = "UPDATE DbResAttrGrpMember u set relationSeq = :relationSeq where u.attrGrpId = :attrGrpId")
//	void updateAttrGrpMemberRelationSeq(@Param("relationSeq") String relationSeq,@Param("attrGrpId") Long attrGrpId);	
}
