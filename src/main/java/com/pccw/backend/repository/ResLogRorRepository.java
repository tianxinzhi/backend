package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.entity.DbResLogRor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


public interface ResLogRorRepository extends BaseRepository<DbResLogRor> {

    DbResLogRor findDbResLogRorByLogOrderId(String logTxtBum);

}
