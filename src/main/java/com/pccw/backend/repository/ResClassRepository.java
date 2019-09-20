package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResClass;
import org.springframework.stereotype.Repository;

@Repository
public interface ResClassRepository extends BaseRepository<DbResClass> {
	
	//  DbResClass findByClassName(String className);

}
