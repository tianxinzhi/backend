package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResProcess;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ResProcessRepository extends BaseRepository<DbResProcess> {



    @Query(value = "SELECT\n" +
            "\t RP.id \t\n" +
            "FROM\n" +
            "\t RES_PROCESS rp,\n" +
            "\t RES_PROCESS_DTL rpd\n" +
            "WHERE\n" +
            "\tRPD.PROCESS_ID = RP.ID   AND  rp.STATUS='PENDING'  AND rpd.STATUS='PENDING'  \n" +
            "AND  exists  \n" +
            "(\n" +
            "\tSELECT\n" +
            "\t\t*\n" +
            "\tFROM\n" +
            "\t\t RES_ACCOUNT_ROLE\n" +
            "\tWHERE\n" +
            "\t\t ACCOUNT_ID =?1 \n" +
            "    and  RPD.ROLE_ID=ROLE_ID\n" +
            ")\n" +
            "AND \t\t\n" +
            "\t RP.log_order_nature=nvl(?2,RP.log_order_nature)\n" +
            "AND \n" +
            "\t RP.repo_id=nvl(?3,RP.repo_id) \n" +
            "AND RP.log_txt_num LIKE CONCAT(CONCAT('%',?4),'%') \n" +
            "\n" +
            "AND  RP.create_at BETWEEN ?5\n" +
            "\tAND ?6",nativeQuery = true)
    List<Long> findIdsByPending(long accountId,String nature,String repoId,String txtNum,long timeBegin,long timeEnd);

    List<DbResProcess> findDbResProcessesByIdIn(List ids);
}
