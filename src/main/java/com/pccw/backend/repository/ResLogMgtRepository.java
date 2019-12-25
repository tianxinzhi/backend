package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResLogMgt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


public interface ResLogMgtRepository extends BaseRepository<DbResLogMgt> {

    DbResLogMgt findDbResLogMgtByLogTxtBum(String logTxtBum);

    List<DbResLogMgt> getDbResLogMgtsByAdjustReasonId(long adjustReasonId);

    @Query(value = "SELECT\n" +
            "\trlmd.id \"id\",\n" +
            "\trlmd.DTL_REPO_ID \"repoId\",\n" +
            "\trr.REPO_CODE \"repoCode\",\n" +
            "\trlmd.DTL_SKU_ID \"skuId\" ,\n" +
            "\trs.SKU_NAME \"skuName\",\n" +
            "\trlmd.DTL_QTY \"qty\",\n" +
            "\trlmd.ACTIVE \"active\",\n" +
            "\trlmd.CREATE_AT \"createAt\",\n" +
            "\t(SELECT ACCOUNT_NAME from RES_ACCOUNT where ID = rlmd.CREATE_BY) \"createAccountName\",\n" +
            "\trlmd.UPDATE_AT \"updateAt\",\n" +
            "\t(SELECT ACCOUNT_NAME from RES_ACCOUNT where ID = rlmd.UPDATE_BY) \"updateAccountName\" \n" +
            "FROM\n" +
            "\tRES_LOG_MGT_DTL rlmd\n" +
            "\tLEFT JOIN RES_LOG_MGT rlm ON rlmd.LOG_MGT_ID = rlm.ID\n" +
            "\tLEFT JOIN RES_REPO rr ON rr.id = rlmd.DTL_REPO_ID\n" +
            "\tLEFT JOIN RES_SKU rs ON rs.id = rlmd.DTL_SKU_ID \n" +
            "WHERE\n" +
            "\trlm.LOG_ORDER_NATURE = 'STHR' \n" +
            "\tand rlmd.DTL_REPO_ID = nvl(?1,rlmd.DTL_REPO_ID)\n" +
            "\tand rlmd.DTL_SKU_ID = nvl(?2,rlmd.DTL_SKU_ID)\n" +
            "ORDER BY\n" +
            "\trlmd.CREATE_AT DESC",nativeQuery = true)
    List<Map> getStockThreshold(@Param("repoId") String repoId, @Param("skuId") String skuId);
}
