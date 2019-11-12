package com.pccw.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pccw.backend.entity.DbResSkuRepo;



public interface  ResSkuRepoRepository extends BaseRepository<DbResSkuRepo>{

    // Page<DbResSkuRepo> findAll(Specification<DbResSkuRepo> spec,Pageable p);

}