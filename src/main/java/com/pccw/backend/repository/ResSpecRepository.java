package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResSpec;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;


public interface ResSpecRepository extends BaseRepository<DbResSpec> {

    @Query(value = "SELECT  rs.spec_name specName, rav.attr_value, \n" +
            "        (SELECT ra.attr_name FROM  res_attr ra WHERE  ra.ID = rsa.attr_id)  attr_name\n" +
            "FROM res_spec rs,res_attr_value rav,res_spec_attr rsa \n" +
            "WHERE rs.ID = rsa.spec_id  AND rsa.attr_value_id = rav.ID"
            ,nativeQuery =true )  //t.active qqq, s.SPEC_NAME www
    List<Map> findTest();

    List<DbResSpec> findById(long id);

}
