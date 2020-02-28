package com.pccw.backend.repository;

import com.pccw.backend.entity.DbResAttrAttrValue;
import com.pccw.backend.entity.DbResAttrValue;
import com.pccw.backend.entity.DbResSku;
import com.pccw.backend.entity.DbResSkuLis;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResSkuLisRepository extends BaseRepository<DbResSkuLis>{

    List<DbResSkuLis> getDbResSkuLissBySkuCode(String skuCode);

    DbResSkuLis getDbResSkuLisBySkuId(DbResSku skuId);

}
