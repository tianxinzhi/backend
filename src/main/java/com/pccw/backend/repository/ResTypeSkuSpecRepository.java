package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResSku;
import com.pccw.backend.entity.DbResType;
import com.pccw.backend.entity.DbResTypeSkuSpec;

import java.util.List;


public interface ResTypeSkuSpecRepository extends BaseRepository<DbResTypeSkuSpec> {

   /* @Query(value = "select * from RES_TYPE t,RES_SPEC s where t.active =s.SPEC_ID"
            ,nativeQuery =true )  //t.active qqq, s.SPEC_NAME www
    List<DbResTypeSkuSpec> findTest();*/

   List<DbResTypeSkuSpec> getDbResTypeSkuSpecsBySpecId(long specId);

   List<DbResTypeSkuSpec> getDbResTypeSkuSpecsByType(DbResType type);

   DbResTypeSkuSpec getDbResTypeSkuSpecBySku(DbResSku sku);

   DbResTypeSkuSpec getDbResTypeSkuSpecsBySkuAndType(DbResSku sku, DbResType type);
}
