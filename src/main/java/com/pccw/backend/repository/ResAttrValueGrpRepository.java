package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResAttrValueGrp;

@Profile("dev1")
public interface ResAttrValueGrpRepository extends JpaRepository<DbResAttrValueGrp, Long> {

}
