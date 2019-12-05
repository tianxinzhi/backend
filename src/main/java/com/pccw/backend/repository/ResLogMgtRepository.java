package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResLogMgt;


public interface ResLogMgtRepository extends BaseRepository<DbResLogMgt> {

    DbResLogMgt findDbResLogMgtByLogTxtBum(String logTxtBum);
}
