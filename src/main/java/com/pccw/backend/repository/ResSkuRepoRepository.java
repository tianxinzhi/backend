package com.pccw.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pccw.backend.entity.DbResSkuRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


public interface  ResSkuRepoRepository extends BaseRepository<DbResSkuRepo>{

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

    @Query(value = "SELECT * from RES_SKU_REPO rsr where rsr.SKU_ID =?1  and rsr.STOCK_TYPE_ID = ?2",nativeQuery = true)
    DbResSkuRepo findByRepoIdAndStockTypeId(@Param("id") Long id,
                                            @Param("stockTypeIdTo") Long stockTypeIdTo);

    @Query(value = "select * from res_sku_repo t where t.repo_id = ?1 and t.sku_id = ?2 and t.stock_type_id = ?3",nativeQuery = true)
    DbResSkuRepo findQtyByRepoAndShopAndType(@Param("shop") long shop,@Param("sku") long sku,@Param("stockType") long stockType);

    @Modifying
    @Query(value = "update res_sku_repo set qty = qty + ?4 where repo_id = ?1 and sku_id = ?2 and stock_type_id = ?3",nativeQuery = true)
    int updateQtyByRepoAndShopAndTypeAndQty(@Param("shop") long shop,@Param("sku") long sku,@Param("stockType")long stockType,@Param("qty")long qty);

    @Query(value = "select rs.id  dtl_Sku_Id, rs.sku_code  sku_Code from res_sku rs \n" +
            "where exists \n" +
            "    (select * from res_sku_repo  rsr\n" +
            "       where rsr.repo_id= ?1 and  rsr.sku_id=rs.id\n" +
            "        and rsr.stock_type_id =\n" +
            "         decode( (select count(1)  \n" +
            "                  from res_repo tor\n" +
            "                  where tor.id= ?2  and tor.repo_type='W' )\n" +
            "                   ,0\n" +
            "                   ,3\n" +
            "                   ,rsr.stock_type_id ) )",nativeQuery = true)
    List<Map<String, Object>> findByTypeIdAndRepoId(@Param("idFrom") Long id, @Param("idTo") Long idTo);

}
