package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResAdrLkup;
import com.pccw.backend.entity.DbResArea;
import com.pccw.backend.entity.DbResRepo;

@Profile("dev1")
public interface ResAdrLkupRepository extends JpaRepository<DbResAdrLkup, Long> {

	DbResAdrLkup findByAreaAndRepoAndAdrAreaTypeAndAdrAreaId(DbResArea area, DbResRepo repo, String adrAreaType, Long adrAreaId);

}
