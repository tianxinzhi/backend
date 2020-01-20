package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResSku;
import com.pccw.backend.entity.DbResType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


public interface ResSkuRepository extends BaseRepository<DbResSku> {

    @Query(value = "SELECT  rsa.SPEC_ID spec,  rs.SPEC_NAME specName, " +
            "ra.ID attr, WM_CONCAT(rav.ID) attrValue, " +
            "ra.ATTR_NAME attrName, WM_CONCAT(rav.attr_value) attrValueName FROM" +
            " RES_SPEC_ATTR rsa LEFT JOIN " +
            "RES_ATTR ra ON ra.ID = rsa.ATTR_ID LEFT JOIN " +
            "RES_ATTR_VALUE rav ON rav.id = rsa.ATTR_VALUE_ID " +
            "LEFT JOIN    RES_SPEC rs ON rs.id = rsa.SPEC_ID WHERE rsa.SPEC_ID " +
            "= (SELECT rtss.SPEC_ID  FROM  RES_TYPE_SKU_SPEC rtss WHERE rtss.TYPE_ID = ?1)" +
            "GROUP BY ra.ATTR_NAME,rsa.SPEC_ID, rs.SPEC_NAME , ra.ID ORDER BY rsa.SPEC_ID ",nativeQuery = true)
    /**
     * 根据type查询spec及其attr相关信息
     */
    List<Map> getAllSpecsByType(@Param("typeId") long typeId);

    @Query(value = "select T3.type_name typeName,T3.id type,t5.spec_name specName,t5.id spec,t6.attr_name attrName,t6.id attr, " +
            "WM_CONCAT(DISTINCT t7.attr_value) attrValueName ,WM_CONCAT(DISTINCT t7.id) attrValue " +
            "from RES_TYPE t3 inner join  res_sku_type t1  on T3.id = t1.type_id " +
            "left join RES_TYPE_SKU_SPEC t4 on t1.type_id = t4.type_id " +
            "left join res_spec t5 on t5.id = t4.spec_id " +
            "left join RES_SKU_ATTR_VALUE t2 on T1.SKU_ID = T2.SKU_ID " +
            "left join RES_ATTR t6 on t6.id = t2.attr_id " +
            "left join RES_ATTR_VALUE t7 on t7.id = t2.attr_value_id " +
            "where T1.sku_id  = ?1 GROUP BY T3.type_name ,T3.id ,t5.spec_name,t5.id ,t6.attr_name ,t6.id ",nativeQuery = true)
    /**
     * 根据sku查询type，spec，attr，attrValue信息
     */
    List<Map> getTypeDtlsBySku(@Param("skuId") long skuId);

    List<DbResSku> getDbResSkusBySkuCode(String skuCode);

    DbResSku findFirst1BySkuCode(String sku_id);

}
