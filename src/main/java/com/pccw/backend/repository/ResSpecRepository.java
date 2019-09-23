package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResSpec;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ResSpecRepository extends BaseRepository<DbResSpec> {

   /* @Query(value = "select * from RES_TYPE t,RES_SPEC s where t.active =s.SPEC_ID"
            ,nativeQuery =true )  //t.active qqq, s.SPEC_NAME www
    List<DbResSpec> findTest();*/
}
