package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResTypeSkuSpec;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ResTypeSkuSpecRepository extends BaseRepository<DbResTypeSkuSpec> {

   /* @Query(value = "select * from RES_TYPE t,RES_SPEC s where t.active =s.SPEC_ID"
            ,nativeQuery =true )  //t.active qqq, s.SPEC_NAME www
    List<DbResTypeSkuSpec> findTest();*/
}
