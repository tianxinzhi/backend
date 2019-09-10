package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.pccw.backend.entity.DbResClass;
import org.springframework.stereotype.Repository;

@Repository
public interface ResClassRepository extends BaseRepository<DbResClass> {
	
	//  DbResClass findByClassName(String className);

}
