package com.pccw.backend.repository;

import com.pccw.backend.entity.DbResAttrAttrValue;
import com.pccw.backend.entity.DbResAttrValue;
import com.pccw.backend.entity.DbResClass;
import com.pccw.backend.entity.DbResClassLis;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResClassLisRepository extends BaseRepository<DbResClassLis>{
    List<DbResClassLis> getDbResClassLissByClassDesc(String classDesc);
}
