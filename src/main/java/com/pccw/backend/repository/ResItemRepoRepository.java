package com.pccw.backend.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


import com.pccw.backend.entity.DbResItemRepo;



public interface  ResItemRepoRepository extends JpaRepository<DbResItemRepo, Long> {

	Page<DbResItemRepo> findAll(Specification<DbResItemRepo> spec,Pageable p);

}