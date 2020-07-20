package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResLogMgt;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


public interface ResLogMgtRepository extends BaseRepository<DbResLogMgt> {

    DbResLogMgt findDbResLogMgtByLogTxtBum(String logTxtBum);

    List<DbResLogMgt> findDbResLogMgtsByLogTxtBum(String logTxtBum);

    List<DbResLogMgt> getDbResLogMgtsByAdjustReasonId(long adjustReasonId);

    @Query(value = "SELECT \n" +
            " rlmd.id \"id\",\n" +
            " rlmd.DTL_REPO_ID \"repoId\",\n" +
            " rr.REPO_CODE \"repoCode\",\n" +
            " rlmd.DTL_SKU_ID \"skuId\" ,rlmd.DTL_SUBIN \"status\",\n" +
            " rs.SKU_NAME \"skuName\",\n" +
            " CONCAT('<=',  rlmd.DTL_QTY ) \"qty\",\n" +
            " rlmd.DTL_QTY \"qtyy\",\n" +
            " rlm.LOG_TXT_NUM \"logTxtBum\",\n" +
            " rlm.REMARK \"remark\",\n" +
            " rlmd.ACTIVE \"active\",\n" +
            " rlmd.CREATE_AT \"createAt\",\n" +
            " (SELECT ACCOUNT_NAME from RES_ACCOUNT where ID = rlmd.CREATE_BY) \"createAccountName\",\n" +
            " rlmd.UPDATE_AT \"updateAt\",\n" +
            " (SELECT ACCOUNT_NAME from RES_ACCOUNT where ID = rlmd.UPDATE_BY) \"updateAccountName\" \n" +
            " FROM\n" +
            " RES_LOG_MGT_DTL rlmd\n" +
            " LEFT JOIN RES_LOG_MGT rlm ON rlmd.LOG_MGT_ID = rlm.ID\n" +
            " LEFT JOIN RES_REPO rr ON rr.id = rlmd.DTL_REPO_ID\n" +
            " LEFT JOIN RES_SKU rs ON rs.id = rlmd.DTL_SKU_ID \n" +
            " WHERE\n" +
            " rlm.LOG_ORDER_NATURE = 'Stock Threshold(STHR)' \n" +
            " and rlmd.DTL_REPO_ID = nvl(?1,rlmd.DTL_REPO_ID)\n" +
//            " and rlmd.DTL_SKU_ID = nvl(?2,rlmd.DTL_SKU_ID)\n" +
            " ORDER BY\n" +
            " rlmd.CREATE_AT DESC",nativeQuery = true)
    List<Map> getStockThreshold(@Param("repoId") String repoId);
//    List<Map> getStockThreshold(@Param("repoId") String repoId, @Param("skuId") String skuId);

    @Modifying
    @Query(value = "update RES_LOG_MGT_DTL set DTL_QTY =?1 where id = ?2",nativeQuery = true)
    int updateDbResLogMgtDtlById(@Param("qtyy") Long qtyy, @Param("id") Long id);

    List<DbResLogMgt> findByLogOrderNatureIn(List<String> status);

    @Query(value = "SELECT DISTINCT MGT.* FROM RES_LOG_MGT mgt, RES_LOG_MGT_DTL mgtdtl WHERE MGTDTL.LOG_MGT_ID=MGT.id AND MGT.LOG_ORDER_NATURE IN ?1 AND MGTDTL.DTL_SKU_ID=?2",nativeQuery = true)
    List<DbResLogMgt> findByLogOrderNatureInAndSkuId(List<String> Natures,Long skuId);


    List<DbResLogMgt> findByDeliveryNumber(String deliveryNumber);

}
