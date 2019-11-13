package com.pccw.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pccw.backend.entity.DbResSkuRepo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


public interface  ResSkuRepoRepository extends JpaRepository<DbResSkuRepo, Long>,JpaSpecificationExecutor{

    // Page<DbResSkuRepo> findAll(Specification<DbResSkuRepo> spec,Pageable p);

    @Query(value = "select ROWNUM id, a.* from (select RS.SKU_DESC description,RS.SKU_CODE sku,RR.REPO_CODE AS store,\n" +
            "sum(nvl(decode(RST.STOCKTYPE_NAME ,'Intransit(DEL)',RSR.QTY),0)) DELQTY,\n" +
            "sum(nvl(decode(RST.STOCKTYPE_NAME ,'Faulty(FAU)',RSR.QTY),0))    FAUQTY,\n" +
            "sum(nvl(decode(RST.STOCKTYPE_NAME ,'Reserved(RES)',RSR.QTY),0))  RESQTY,\n" +
            "sum(nvl(decode(RST.STOCKTYPE_NAME ,'Available(AVL)',RSR.QTY),0)) AVLQTY,\n" +
            "sum(nvl(decode(RST.STOCKTYPE_NAME ,'Demo(DEM)',RSR.QTY),0))      DEMQTY,\n" +
            "sum(nvl(decode(RST.STOCKTYPE_NAME ,'Reserved With AO(RAO)',RSR.QTY),0)) RAOQTY\n" +
            "FROM   \n" +
            "  RES_SKU RS,\n" +
            "  RES_REPO RR,\n" +
            "\tRES_STOCK_TYPE RST,\n" +
            "\tRES_SKU_REPO RSR\n" +
            "WHERE\n" +
            "   RS.ID = RSR.SKU_ID\n" +
            "   AND RR.ID = RSR.REPO_ID\n" +
            "   AND RST.ID = RSR.STOCK_TYPE_ID\n" +
            "\t AND RS.sku_code LIKE CONCAT(CONCAT('%',:skuNum),'%')\n" +
            "AND RSR.REPO_ID=nvl(:repoNum,RSR.REPO_ID)\n"+
            "GROUP BY RS.SKU_CODE,RR.REPO_CODE,RS.SKU_DESC) a",nativeQuery = true)
    List<Map> getStockBalanceInfo(@Param("skuNum") String skuNum,@Param("repoNum") String repoNum);
}