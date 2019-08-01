package com.pccw.backend.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResSpecVer;


@Profile("dev1")
public interface ResSpecVerRepository extends JpaRepository<DbResSpecVer, Long> {
	
	List<DbResSpecVer> findByVer(Long ver);

}
