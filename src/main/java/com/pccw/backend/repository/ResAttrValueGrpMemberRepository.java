package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbresAttrValueGrpMember;

@Profile("dev1")
public interface ResAttrValueGrpMemberRepository extends JpaRepository<DbresAttrValueGrpMember, Long> {

	DbresAttrValueGrpMember findByAttrValueGrpId(Long attrValueGrpId);
	
	DbresAttrValueGrpMember findByAttrValueId(Long attrValueId);
	
//	@Transactional
//	@Modifying
//	@Query(value = "UPDATE DbresAttrValueGrpMember u set relationSeq = :relationSeq where u.attrValueGrpId = :attrValueGrpId")
//	void updateAttrGrpMemberRelationSeq(@Param("relationSeq") String relationSeq,@Param("attrValueGrpId") Long attrValueGrpId);	
}
