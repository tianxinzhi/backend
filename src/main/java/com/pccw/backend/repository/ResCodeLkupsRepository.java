package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResCodeLkup;

@Profile("dev1")
public interface ResCodeLkupsRepository extends JpaRepository<DbResCodeLkup, Long> {

	DbResCodeLkup findByGrpIdAndDescription(String grpId, String description);

}
