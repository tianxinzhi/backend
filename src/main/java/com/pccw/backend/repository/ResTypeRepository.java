package com.pccw.backend.repository;

import com.pccw.backend.entity.DbResSpec;
import com.pccw.backend.entity.DbResType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResTypeRepository extends BaseRepository<DbResType> {

   @Query(value = "SELECT rsa.SPEC_ID specId,ra.ATTR_NAME attrName,nvl(rav.attr_value,rav.value_from||'~'||rav.value_to) attrValue from RES_SPEC_ATTR rsa\n" +
           "LEFT JOIN RES_ATTR ra on ra.ID=rsa.ATTR_ID\n" +
           "LEFT JOIN RES_ATTR_VALUE rav on rav.id =rsa.ATTR_VALUE_ID\n" +
           "where rsa.SPEC_ID = ?1", nativeQuery = true)
   List<Map> specSearch(@Param("id") long id);

   @Query(value = "from DbResSpec where id =?1")
   DbResSpec findBySpecId(@Param("id") long id);

   List<DbResType> getDbResTypesByTypeCode(String typeCode);

   @Query(value = "from DbResSpec where verId =?1 and specName = ?2")
   DbResSpec findByVerAndSpecName(@Param("verId") String verId, @Param("specName") String specName);
}
