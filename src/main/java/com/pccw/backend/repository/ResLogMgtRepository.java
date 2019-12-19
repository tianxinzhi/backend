package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResLogMgt;

import java.util.List;


public interface ResLogMgtRepository extends BaseRepository<DbResLogMgt> {

    DbResLogMgt findDbResLogMgtByLogTxtBum(String logTxtBum);

    List<DbResLogMgt> getDbResLogMgtsByAdjustReasonId(long adjustReasonId);
}
