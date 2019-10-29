package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResSku;
import com.pccw.backend.entity.DbResSkuType;


public interface ResSkuTypeRepository extends BaseRepository<DbResSkuType> {

  DbResSkuType findBySkuAndTypeId(DbResSku sku, long typeId);

    DbResSkuType findBySku(DbResSku sku);
}
