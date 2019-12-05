package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResLogMgt;

import java.util.List;


public interface ResLogMgtRepository extends BaseRepository<DbResLogMgt> {

    List<DbResLogMgt> findDbResLogMgtByLogTxtBum(String logTxtBum);
}
