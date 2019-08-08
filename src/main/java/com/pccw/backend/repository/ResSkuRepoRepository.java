package com.pccw.backend.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


import com.pccw.backend.entity.DbResSkuRepo;



public interface  ResSkuRepoRepository extends JpaRepository<DbResSkuRepo, Long> {

    Page<DbResSkuRepo> findAll(Specification<DbResSkuRepo> spec,Pageable p);

}